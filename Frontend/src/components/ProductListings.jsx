import React,{useState} from "react";
import ProductCard from "./ProductCard";
import SearchBox from "./SearchBox";
import DropDown from "./DropDown";
import { useLoaderData } from "react-router-dom";


const sortList=["Popularity","Price Low to High","Price High to Low"];

export default function ProductListings({products}) {
  const[searchtext, setsearchtext]=useState("");
  const[SelectedSort,setSelectedSort]=useState("Popularity");

  function handleSearchChange(inputsearch){
    setsearchtext(inputsearch);}

     function handleSortChange(inputsort){
    setSelectedSort(inputsort);}


  let filteredAndSorted=Array.isArray(products)?
  products.filter((product)=> product.name.toLowerCase().includes(searchtext.toLowerCase())
     || product.description.toLowerCase().includes(searchtext.toLowerCase())
    )
  :[]

  switch(SelectedSort){
    case "Price High to Low":
      filteredAndSorted=filteredAndSorted.sort((a,b)=>parseFloat(b.price)-parseFloat(a.price));
      break;
    case "Price Low to High":
      filteredAndSorted=filteredAndSorted.sort((a,b)=>parseFloat(a.price)-parseFloat(b.price));
      break;
    case "Popularity":
    default:
      filteredAndSorted=filteredAndSorted.sort((a,b)=>parseInt(b.popularity)-parseInt(a.popularity));
      break;
  }
    
  return (
    <div className="max-w-[1152px] mx-auto">
      <div className="flex flex-col sm:flex-row justify-between items-center gap-4 pt-12">
        <SearchBox label="Search" 
        placeholder="Search products..." 
        value={searchtext} 
        handleSearch={(value)=>handleSearchChange(value)}/>
        <DropDown label="Sort by" 
        options={sortList} 
        value={SelectedSort} 
        handleSort={(value)=>handleSortChange(value)}/>
      </div>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-y-8 gap-x-6 py-12">
        {filteredAndSorted.length > 0 ? (
          filteredAndSorted.map((product) => (
            <ProductCard key={product.productId} product={product} />
          ))
        ) : (
          <p className="text-center font-primary font-bold text-lg text-primary">
            No products found
          </p>
        )}
      </div>
    </div>
  );
}


