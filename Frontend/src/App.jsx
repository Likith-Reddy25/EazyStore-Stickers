import Header from "./components/Header";
import Footer from "./components/footer/Footer";
import React from "react";
import Home from "./components/Home";
import { Outlet } from "react-router-dom";
import { useNavigation } from "react-router-dom";
import ChatWidget from "./components/ChatWidget";

function App() {
  return (
    <>
      <Header />
      <Outlet/>
      <ChatWidget/>
      <Footer/>
    </>
  );
}

export default App;
