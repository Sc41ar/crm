import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import DashBoard from "./pages/DashBoard";
import Singup from "./pages/Signup";
import Login from "./pages/Login";
import NotFound from "./pages/NotFound";
import Contacts from "./pages/Contacts";
import Deals from "./pages/Deals";
import Tasks from "./pages/Tasks";

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
    path: "/contacts",
    element: <Contacts />,
  },
  {
    path: "/deals",
    element: <Deals />,
  },
  {
    path: "/tasks",
    element: <Tasks />,
  },
  {
    path: "*",
    element: <NotFound />,
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
