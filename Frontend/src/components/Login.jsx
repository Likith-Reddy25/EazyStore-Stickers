import React,{useEffect, useState} from "react";
import PageTitle from "./PageTitle";
import { Link, Form, useActionData, useNavigate, useNavigation, Navigate } from "react-router-dom";
import apiClient from "../api/apiClient";
import { toast } from "react-toastify";
import { useAuth } from "../store/auth-context";


export default function Login() {
  const actionData=useActionData();
  const navigation=useNavigation();
  const isSubmitting= navigation.state==="submitting";
  const navigate= useNavigate();
  const {loginSuccess}=useAuth();
  const from= sessionStorage.getItem("redirectPath") || "/home";

  useEffect(()=>{
    if(actionData?.success){
      loginSuccess(actionData.jwtToken, actionData.user);
      sessionStorage.removeItem("redirectPath"); 
      navigate(from); 
      // the above 2 lines are used to navigate to previously existed page.
    }else if(actionData?.errors){
      toast.error(actionData.errors.message || "Login Failed");
    }
  }, [actionData]);

  const labelStyle =
    "block text-lg font-semibold text-primary ";
  const textFieldStyle =
    "w-full px-4 py-2 text-base border rounded-md transition border-primary focus:ring focus:ring-dark focus:outline-none text-gray-800   placeholder-gray-400 ";
  return (
    <div className="min-h-screen flex items-center justify-center font-primary ">
      <div className="bg-white  shadow-md rounded-lg max-w-md w-full px-8 py-6">
        {/* Title */}
        <PageTitle title="Login" />
        {/* Form */}
        <Form method="POST" className="space-y-6">
          {/* Email Field */}
          <div>
            <label htmlFor="username" className={labelStyle}>
              Username
            </label>
            <input
              id="username"
              type="text"
              name="username"
              placeholder="Your Username"
              required
              className={textFieldStyle}
            />
          </div>

          {/* Password Field */}
          <div>
            <label htmlFor="password" className={labelStyle}>
              Password
            </label>
            <input
              id="password"
              type="password"
              name="password"
              placeholder="Your Password"
              required
              minLength={4}
              maxLength={20}
              className={textFieldStyle}
            />
          </div>

          {/* Submit Button */}
          <div>
            <button
              type="submit"
              className="w-full px-6 py-2 text-white  text-xl rounded-md transition duration-200 bg-primary  hover:bg-dark "
            >
              Login
            </button>
          </div>
        </Form>

        {/* Register Link */}
        <p className="text-center text-gray-600 ">
          Don't have an account?{" "}
          <Link
            to="/register"
            className="text-primary  hover:text-dark  transition duration-200"
          >
            Register Here
          </Link>
        </p>
      </div>
    </div>
  );
}

export async function loginAction({request}){
  const data= await request.formData();

  const loginData={
    username: data.get("username"),
    password: data.get("password")
  };
  try{
    const response= await apiClient.post("/auth/login", loginData);
    const {message, user, jwtToken}= response.data;
    return {success: true, message, user, jwtToken};
  }
  catch(error){
    if(error.response?.status==401){
      return {
        success:false,
        errors:{message:"Invalid username or password"},
      };
    }
    throw new Response(
      error.response?.data?.message ||
      error.message ||
      "Failed to login. Please try again.",
      {status:error.response?.status || 500}
    )
  }
}