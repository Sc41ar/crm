import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import './index.css'
import  Singup  from "./pages/Signup.jsx"
import Login from "./pages/Login.jsx"
import NotFound from "./pages/NotFound.jsx"
import DashBoard from "./pages/DashBoard.jsx"

const router = createBrowserRouter([
  {
    path: "/", 
    element: <DashBoard />,
  },
  {
    path: "/signup", 
    element: <Singup />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/notfound",
    element: <NotFound />,
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
    {/* <BrowserRouter>
      <App />
    </BrowserRouter> */}
  </React.StrictMode>,
)
