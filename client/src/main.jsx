import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import './index.css'
import  Singup  from "./pages/Signup.jsx"

const router = createBrowserRouter([
  {
    path: "/",
    element: <Singup />,
  },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
    {/* <BrowserRouter>
      <App />
    </BrowserRouter> */}
  </React.StrictMode>,
)
