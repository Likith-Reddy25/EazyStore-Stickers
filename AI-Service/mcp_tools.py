import os
import httpx
from typing import Dict, Any, Optional
from dotenv import load_dotenv

load_dotenv()

SPRING_BOOT_BASE_URL= os.getenv("SPRING_BOOT_BASE_URL", "http://localhost:8080/api/v1")

async def get_order_details(order_id: int, jwt_token: Optional[str]=None)-> Dict[str, Any]:
    """
    Fetch the details and status of a specific order from spring boot.
    Returns orderStatus, totalPrice, trackingNumber, deliveryCarrier, estimatedDelivery, items and address.
    """

    headers={}
    if jwt_token:
        headers["Authorization"]= f"Bearer {jwt_token}"
    
    url= f"{SPRING_BOOT_BASE_URL}/orders/{order_id}"
    try:
        async with httpx.AsyncClient(timeout=10.0) as client:
            response= await client.get(url, headers=headers)
            if response.status_code==200:
                return {"success": True, "data":response.json()}
            elif response.status_code==404:
                return {"success": False, "message": f"Order #{order_id} was not found"}
            else:
                return {"success": False, "message" :f"Failed to fetch order: HTTP {response.status_code}"}
    except Exception as e:
        return {"success": False, "message": f"Could not connect to Spring Boot Backend: {str(e)}"}
    

async def cancel_order(order_id: int, jwt_token: Optional[str]=None)-> Dict[str, Any]:
    """
    Cancels an order if it has not shipped or deilvered yet
    """

    headers={}
    if jwt_token:
        headers["Authorization"]= f"Bearer {jwt_token}"

    url=f"{SPRING_BOOT_BASE_URL}/orders/{order_id}/cancel"
    try:
        async with httpx.AsyncClient(timeout=10.0) as client:
            response= await client.get(url,headers=headers)
            if response.status_code==200:
                return {"success": True, "message":f"Order #{order_id} has been cancelled succesfully"}
            else:
                error_msg= response.json().get("message", "Cancellation Rejected")
                return {"success": True, "message": error_msg}
    except Exception as e:
        return {"success": False, "message":f"Network Error contacting backend: {str(e)}"}
    

async def update_shipping_address(
        order_id: int,
        street: str,
        city: str,
        state: str,
        postal_code: str,
        country: str="India",
        jwt_token: Optional[str]= None
)-> Dict[str, Any]:
    """
    Updated the shipping address for order that is still processing.
    """
    headers={}
    if jwt_token:
        headers["Authorization"]= f"Bearer {jwt_token}"

    payload= {
        "street": street,
        "city": city,
        "state": state,
        "postalCode": postal_code,
        "country": country
    }

    url= f"{SPRING_BOOT_BASE_URL}/orders/{order_id}/address"

    try:
        async with httpx.AsyncClient(timeout=10.0) as client: 
            response= await client.put(url,json=payload, headers= headers)
            if response.status_code==200:
                return {"success": True, "message": f"Shipping Address of Order #{order_id} updated successfully"}
            else:
                error_msg= response.json().get("message", "Address update rejected")
                return {"success": False, "message":error_msg}
    except Exception as e:
        return {"success": False, "message":f"Network error contacting backend: {str(e)}"}


            