import React from "react";
import FormBlock from "../components/FormBlock";
import Button from "../components/Button";
import "../index.css";
import { Link } from "react-router-dom";
import axios from "axios";
export default function Component() {
  const handleSubmit = async (event) => {
    event.preventDefault();

    const name = event.target.email.value;
    const password = event.target.password.value;

    axios
      .post(
        "http://localhost:8080/crm/login",
        {
          emailOrUsername: name,
          password: password,
        },
        { withCredentials: true }
      )
      .then((response) => {
        console.log(response);
        if (response.status === 200) {
          // Handle successful login
          // Redirect to dashboard or home page
        } else {
          // Handle login error
          console.error("Login failed");
          // Show error message to user
        }
      });
  };

  return (
    <div className="w-dvw">
      <div className="flex min-h-[100dvh] items-center justify-center  bg-gray-100 px-4 dark:bg-gray-950">
        <div className="w-full max-w-md space-y-4 rounded-lg bg-white p-6 shadow-lg dark:bg-gray-900">
          <div className="text-center">
            <h1 className="text-3xl font-bold">Welcome Back</h1>
            <p className="text-gray-500 dark:text-gray-400">
              Sign in to your account to continue.
            </p>
          </div>
          <form className="flex flex-col" onSubmit={handleSubmit}>
            <FormBlock
              id="email"
              label="Email or username"
              placeholder="email or username"
              type="text"
              required
              className="mb-4 flex flex-col"
            />
            <FormBlock
              id="password"
              label="Password"
              placeholder="password"
              type="password"
              required
              className="mb-4 flex flex-col"
            />
            <Button
              type="submit"
              className="w-fit self-center rounded-md  bg-blue-600 dark:bg-blue-950 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-blue-700 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
            >
              Sign in
            </Button>
          </form>
          <div className="text-center text-sm text-gray-500 dark:text-gray-400">
            Don't have an account?{" "}
            <Link to="/signup" className="font-medium hover:underline">
              Register
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
