import React, { useState } from "react";
import "../index.css";
import FormBlock from "../components/FormBlock";
import Button from "../components/Button";
import axios from "axios";
import { Link } from "react-router-dom";
import { BASE_URL } from "../config";

export default function Component() {
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    const name = event.target.name.value;
    const email = event.target.email.value;
    const username = event.target.username.value;
    const password = event.target.password.value;
    const confirmPassword = event.target["confirm-password"].value;

    if (password !== confirmPassword) {
      alert("Passwords do not match");
      return;
    }

    try {
      const data = {
        fio: name,
        email: email,
        username: username,
        password: password,
        role: "ROLE_USER",
      };
      console.log(data);

      const response = await axios.post(`${BASE_URL}:8080/crm/reg`, data);

      if (response.status === axios.HttpStatusCode.Ok) {
        alert("Registration successful");
        sessionStorage.setItem("loginInfo", username);
        window.location.href = "/";
        // Redirect to login page or dashboard
      } else {
        alert("Registration failed: " + response.data.message);
      }
    } catch (error) {
      console.error("An error occurred:", error);
      alert("Registration failed: " + error.message);
    }
  };

  return (
    <div className="w-screen">
      <div className="flex min-h-[100dvh] items-center justify-center  bg-gray-100 px-4 dark:bg-gray-950">
        <div className="flex flex-col w-full justify-items-center items-center max-w-md space-y-4 rounded-lg bg-white p-6 shadow-lg dark:bg-gray-900">
          <div className=" w-full flex flex-col flex-auto max-w-md space-y-4  ">
            <h1 className="self-center w-full">Create an Account</h1>
            <p className=" w-full">Register to get started.</p>
          </div>
          <form
            className="flex flex-col flex-auto justify-self-center min-w-full"
            onSubmit={handleSubmit}
          >
            <FormBlock
              id="name"
              label="Name"
              placeholder="Ivan"
              type="text"
              required
              className="mb-4 flex flex-col"
            />
            <FormBlock
              id="email"
              label="Email"
              placeholder="m@example.com"
              type="email"
              required
              className="mb-4 flex flex-col"
            />
            <FormBlock
              id="username"
              label="Username"
              placeholder="username"
              type="text"
              required
              className="mb-4 flex flex-col"
            />
            <FormBlock
              id="password"
              label="Password"
              type="password"
              placeholder="password"
              required
              onChange={(e) => setPassword(e.target.value)}
              className="mb-4 flex flex-col"
            />
            <FormBlock
              id="confirm-password"
              label="Confirm Password"
              placeholder="confirm password"
              type="password"
              required
              onChange={(e) => setConfirmPassword(e.target.value)}
              className="mb-4 flex flex-col"
            />
            <Button
              className="w-full self-center rounded-md  bg-blue-600 dark:bg-blue-950 px-4 py-2 text-sm font-medium text-white shadow-sm hover:bg-blue-700 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600"
              type="submit"
            >
              Register
            </Button>
          </form>
          <div>
            Already have an account?
            <Link to="/login">Sign In</Link>
          </div>
        </div>
      </div>
    </div>
  );
}
