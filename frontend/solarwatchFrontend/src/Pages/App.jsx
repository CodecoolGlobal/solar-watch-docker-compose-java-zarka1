import { useState } from 'react'
import './App.css'
import { useNavigate } from 'react-router'

function App() {
  const navigate = useNavigate();
  async function toRegistration(){
    navigate("/registration")
  }

  async function toLogin(){
    navigate("/login")
  }

  return (
    <>
      <div>
        <button onClick={toRegistration}>Registration</button>
        <button onClick={toLogin}>Login</button>
      </div>
 
    </>
  )
}

export default App
