import FormBlock from "../components/FormBlock";
import Button from "../components/Button"; 
import "../index.css";
import { Link } from "react-router-dom";


export default function Component() {
  return (
    <div className="flex min-h-[100dvh] items-center justify-center bg-gray-100 px-4 dark:bg-gray-950">
      <div className="w-full max-w-md space-y-4 rounded-lg bg-white p-6 shadow-lg dark:bg-gray-900">
        <div className="text-center">
          <h1 className="text-3xl font-bold">Welcome Back</h1>
          <p className="text-gray-500 dark:text-gray-400">Sign in to your account to continue.</p>
        </div>
        <form className="signup_form">
        <FormBlock id="email" label="Email or username" placeholder="email or username" type="text" required/>
        <FormBlock id="password" label="Password" placeholder="password" type="password" required/>
          <Button type="submit" className="w-full">
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
  )
}