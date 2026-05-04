import { useLocation } from "react-router-dom";
import {
  faArrowLeft,
  faShoppingCart,
  faShoppingBasket,
} from "@fortawesome/free-solid-svg-icons";
import { Link, useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useRef } from "react";
import { useCart } from "../store/cart-context";

export default function ProductDetail() {
  const location = useLocation();
  const product = location.state?.product;
  const navigate = useNavigate();
  const [quantity, setQuantity] = useState(1);
  const {addToCart}=useCart();
//   const zoomRef = useRef(null);
//   const [isHovering, setIsHovering] = useState(false);
//   const [backgroundPosition, setBackgroundPosition] = useState("center");

//   const handleMouseMove = (e) => {
//     const { left, top, width, height } =
//       zoomRef.current.getBoundingClientRect();
//     const x = ((e.pageX - left) / width) * 100;
//     const y = ((e.pageY - top) / height) * 100;
//     setBackgroundPosition(`${x}% ${y}%`);
//   };

//   const handleMouseEnter = () => setIsHovering(true);

//   const handleMouseLeave = () => {
//     setIsHovering(false);
//     setBackgroundPosition("center");
//   };

  const handleViewCart = () => navigate("/cart");

  const handleAddToCart=()=>{
    if(quantity<1) return ;
    addToCart(product, quantity);
  }

  return (
    <div className="min-h-[852px] flex items-center justify-center px-6 py-8 font-primary bg-normalbg ">
      <div className="max-w-5xl w-full mx-auto flex flex-col md:flex-row md:space-x-8 px-6 p-8">
        {/* Product Image with Zoom Effect */}
        <div
        //   ref={zoomRef}
        //   onMouseMove={isHovering ? handleMouseMove : null}
        //   onMouseEnter={handleMouseEnter}
        //   onMouseLeave={handleMouseLeave}
          className="w-full md:w-1/2 border border-gray-300 dark:border-gray-600 rounded-md shadow-lg overflow-hidden bg-cover"
          style={{
            backgroundImage: `url(${product.imageUrl})`,
            backgroundSize: "cover",
            // backgroundPosition: backgroundPosition,
          }}
        >
          <img
            src={product.imageUrl}
            alt={product.name}
            className="w-full h-full opacity-0"
          />
        </div>

        {/* Product Details */}
        <div className="w-full md:w-1/2 flex flex-col space-y-6 mt-8 md:mt-0">
          <Link
            to="/home"
            className="inline-flex items-center text-primary  font-medium hover:text-dark"
          >
            <FontAwesomeIcon icon={faArrowLeft} className="mr-2" />
            Back To All Products
          </Link>

          <div>
            <h1 className="text-3xl font-extrabold text-primary ">
              {product.name}
            </h1>
            <p className="text-lg text-dark  mb-4">
              {product.description}
            </p>
            <div className="text-2xl font-bold text-primary ">
              ${product.price}
            </div>
          </div>

          <div className="flex flex-col space-y-4">
            {/* Quantity Input */}
            <div className="flex items-center space-x-4">
              <label
                htmlFor="quantity"
                className="text-primary "
              >
                Qty:
              </label>
              <input
                type="number"
                id="quantity"
                min="1"
                value={quantity}
                onChange={(e) => setQuantity(parseInt(e.target.value) || 1)}
                className="w-16 px-2 py-1 border rounded-md focus:ring focus:ring-light   text-gray-900 "
              />
            </div>

            {/* Add to Cart Button */}
            <button onClick={handleAddToCart}
            className="w-full px-4 py-2 bg-primary  text-white  rounded-md text-lg font-semibold hover:bg-dark  transition">
              Add to Cart
              <FontAwesomeIcon icon={faShoppingCart} className="ml-2" />
            </button>

            {/* View Cart Button */}
            <button
              onClick={handleViewCart}
              className="w-full px-4 py-2 bg-primary  text-white  rounded-md text-lg font-semibold hover:bg-dark  transition"
            >
              View Cart
              <FontAwesomeIcon icon={faShoppingBasket} className="ml-2" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}