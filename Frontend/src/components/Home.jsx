import PageHeading from "./PageHeading";
import ProductListings from "./ProductListings";
import apiClient from "../api/apiClient";
import { useState, useEffect } from "react";
import { useLoaderData } from "react-router-dom";

// Hooks
export default function Home() {
  const products=useLoaderData();
  return (
    <div className="max-w-[1152px] mx-auto px-6 py-8">
      <PageHeading title="Explore Eazy Stickers!">
        Add a touch of creativity to your space with our wide range of fun and
        unique stickers. Perfect for any occasion!
      </PageHeading>
      <ProductListings products={products} />
    </div>
  );
}


export  async function productsLoader() {
  try {
      const response = await apiClient.get("products"); // Axios GET Request
      return response.data;
    } catch (error) {
       throw new Response(
        error.response?.data?.errorMessage ||
        error.message||"Failed to fetch products. Please tr again",
        {status:error.status||500}
       );
    } 
}

// import { useState, useEffect } from "react";
// import PageHeading from "./PageHeading";
// import ProductListings from "./ProductListings";
// import apiClient from "../api/apiClient";

// export default function Home() {
//   const [products, setProducts] = useState(null); // store fetched products
//   const [loading, setLoading] = useState(true);   // track loading state
//   const [error, setError] = useState(null);       // track fetch errors

//   useEffect(() => {
//     async function fetchProducts() {
//       try {
//         const response = await apiClient.get("products"); // Axios GET request
//         setProducts(response.data);
//       } catch (err) {
//         console.error(err);
//         setError(
//           err.response?.data?.errorMessage ||
//           err.message ||
//           "Failed to fetch products. Please try again."
//         );
//       } finally {
//         setLoading(false);
//       }
//     }

//     fetchProducts();
//   }, []);

//   // Show loading message while waiting for API
//   if (loading) {
//     return (
//       <div className="text-center mt-20 text-lg font-semibold">
//         🚀 Loading products... Waking up the server, please wait!
//       </div>
//     );
//   }



//   // Show error message if fetch fails
//   if (error) {
//     return (
//       <div className="text-center mt-20 text-red-500 font-semibold">
//         ⚠️ {error}
//       </div>
//     );
//   }

//   // Render actual home page content when products are ready
//   return (
//     <div className="max-w-[1152px] mx-auto px-6 py-8">
//       <PageHeading title="Explore Eazy Stickers!">
//         Add a touch of creativity to your space with our wide range of fun and
//         unique stickers. Perfect for any occasion!
//       </PageHeading>
//       <ProductListings products={products} />
//     </div>
//   );
// }
