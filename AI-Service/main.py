from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import Optional
import uvicorn

from agent import process_chat

app= FastAPI(title= "EazyStore ShopAssist AI Service")

# Allow requests from React Frontend (vite running on localhost:5173 or Vercel)

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:5173",
        "http://localhost:3000",
        "https://eazy-store-stickers.vercel.app",
        "https://eazy-store-stickers.vercel.app/",
        "*"
    ],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)

class ChatRequest(BaseModel):
    message: str
    session_id: Optional[str]="default_session"

class ChatResponse(BaseModel):
    response:str
    session_id: str

@app.get("/")
@app.get("/health")
def health_check():
    return {"status": "healthy", "service": "ShopAssist AI"}


@app.post("/api/chat", response_model= ChatResponse)
async def chat_endpoint(request: ChatRequest):
    if not request.message.strip():
        raise HTTPException(status_code=400, detail="Message cannot be empty")
    
    try:
        reply= await process_chat(request.message, thread_id=request.session_id)
        return ChatResponse(response=reply, session_id=request.session_id)
    except Exception as e:
        print(f"Error in chat processing: {str(e)}")
        raise HTTPException(status_code=500, detail=f"AI agent error: {str(e)}")

if __name__=="__main__":
    uvicorn.run("main:app", host="0.0.0.0", port=8000, reload= True)