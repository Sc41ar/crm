import { Routes, Route, Link } from "react-router-dom"
import  Signup  from "./pages/Signup"
import  HomePage  from "./pages/Home"
// import { Login } from "./pages/Login" // Import the Login component

import './App.css'

function App() {
  return (
    <>
      <nav>
        <ul>
          <li><Link to="/signup">Sign Up</Link></li> // Use Link component and 'to' prop for navigation
          <li><Link to="/login">Login</Link></li> // Use Link component and 'to' prop for navigation
        </ul>
      </nav>

      <Routes>
        <Route path="/" element={<HomePage />} /> // Keep the HomePage route at the top
        <Route path="/signup" element={<Signup />} /> // Keep the Signup route
        <Route path="/login" element={<Login />} /> 
        <Route path="*" element={<Signup />} /> 
      </Routes>
    </>
  )
}

export default App
