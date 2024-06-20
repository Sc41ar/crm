import React, { useState } from 'react';
import "../index.css";
import FormBlock from '../components/FormBlock';
import Button from "../components/Button";
import axios from 'axios';
import { Link } from "react-router-dom";


export default function Component() {

    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();

        const name = event.target.name.value;
        const email = event.target.email.value;
        const username = event.target.username.value;
        const password = event.target.password.value;
        const confirmPassword = event.target['confirm-password'].value;

        if (password !== confirmPassword) {
            alert("Passwords do not match");
            return;
        }

        try {
            const data = { fio:name, email:email, username:username, password:password, role:"ROLE_USER" }
            console.log(data)

            const response = await axios.post('http://localhost:8080/crm/reg', 
                data);

            if (response.status === axios.HttpStatusCode.Ok) {
                alert("Registration successful");
                // Redirect to login page or dashboard
            } else {
                alert("Registration failed: " + response.data.message);
            }
        } catch (error) {
            console.error("An error occurred:", error);
            alert("Registration failed: " + error.message);
        }
    }





    return (
        <div className="flex min-h-[100dvh] items-center justify-center bg-gray-200 px-4 dark:bg-gray-800">
            <div className="w-full max-w-md space-y-4 rounded-lg bg-[#374151] p-6 shadow-lg dark:bg-gray-700">
                <div className="text-center">
                    <h1 className="text-3xl font-bold">Create an Account</h1>
                    <p className="text-gray-300 dark:text-gray-400">Register to get started.</p>
                </div>
                <form className="space-y-4" onSubmit={handleSubmit}>
                    <FormBlock id="name" label="Name" placeholder="Ivan" type="text" required />
                    <FormBlock id="email" label="Email" placeholder="m@example.com" type="email" required />
                    <FormBlock id="username" label="Username" placeholder="username" type="text" required />
                    <FormBlock id="password" label="Password" type="password" required onChange={(e) => setPassword(e.target.value)} />
                    <FormBlock id="confirm-password" label="Confirm Password" type="password" required onChange={(e) => setConfirmPassword(e.target.value)} />
                    <Button className="w-full" type="submit">
                        Register
                    </Button>
                </form>
                <div className="text-center text-sm text-gray-300 dark:text-gray-400">
                    Already have an account?
                    <Link className="font-medium hover:underline" to="/signin">
                        Sign In
                    </Link>
                </div>
            </div>
        </div>
    );
}
