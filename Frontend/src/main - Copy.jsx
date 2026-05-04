import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.jsx";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import About from "./components/About.jsx";
import Cart from "./components/Cart.jsx";
import Login, { loginAction } from "./components/Login.jsx";
import Contact  from "./components/Contact.jsx";
import Home from "./components/Home.jsx";
import { createRoutesFromElements } from "react-router-dom";
import { Route } from "react-router-dom";
import { productsLoader } from "./components/Home.jsx";
import ErrorPage from "./components/ErrorPage.jsx";
import { contactAction } from "./components/Contact.jsx";
import { ToastContainer, Bounce } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ProductDetail from "./components/ProductDetail.jsx";
import { CartProvider } from "./store/cart-context.jsx";
import { AuthProvider } from "./store/auth-context.jsx";
import CheckoutForm from "./components/CheckoutForm.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import Register, { registerAction } from "./components/Register.jsx";
import Profile, { profileAction, profileLoader } from "./components/Profile.jsx";
// import Profile from "./components/Profile.jsx";
import Orders from "./components/Orders.jsx";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";
import OrderSuccess from "./components/OrderSuccess.jsx";

const stripePromise=loadStripe("pk_test_51SkGV4QNFtnpGZwyQmILP3598y0z8KoUcNqBOUntt0N0QgTp0Tg3MMBJykCxK07VLB5YDqCsSfQcJf1cKCXhFR8U00PYi9DLXE");

 const routingDefinitions=createRoutesFromElements(
  <Route path="/" element={<App />} errorElement={<ErrorPage/>}>
    <Route index element={<Home />} loader={productsLoader}/>
    <Route path="/home" element={<Home />} loader={productsLoader}/>  
{/* here we are using loader function instead of useState and UseEffect hooks. By the time home component is rendered by react library, the products data will be loaded from backend rest api. */}
    <Route path="/about" element={<About />} />
    <Route path="/contact" element={<Contact />} action={contactAction} />
    <Route path="/login" element={<Login />} action={loginAction}/>
    <Route path="/cart" element={<Cart />} />
    <Route path="/register" element={<Register />} action={registerAction} />
    <Route path="/profile" element={<Profile/>} loader={profileLoader} action={profileAction} />
    <Route path="/orders" element={<Orders/>}/>
    <Route path="/products/:productId" element={<ProductDetail/>}/>  
    <Route element={<ProtectedRoute/>}>
        <Route path="/checkout" element={<CheckoutForm />} />
        <Route path="/order-success" element={<OrderSuccess />} />
    </Route>
    {/* Dynamic routing . it redirects based on path. */}
  </Route>
);

const appRouter=createBrowserRouter(routingDefinitions);
// child components can use the above context to add,remove products in cart

createRoot(document.getElementById("root")).render(
    <StrictMode>
    <Elements stripe={stripePromise}>
    <AuthProvider>
    <CartProvider>
      {/* value is used to specify what are functions or actions we are storing in our context  */}
     <RouterProvider router={appRouter}/>
    </CartProvider>
    </AuthProvider>
    <ToastContainer
    position="top-center"
    autoClose={3000}
    hideProgressBar={false}
    newestOnTop={false}
    draggable
    pauseOnHover
    theme={localStorage.getItem("theme")==="dark"?"dark":"light"}/>
    </Elements>
    </StrictMode>
);
