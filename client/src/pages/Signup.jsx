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
            const data = { fio: name, email: email, username: username, password: password, role: "ROLE_USER" }
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
        <div>
            <div>
                <div>
                    <h1>Create an Account</h1>
                    <p>Register to get started.</p>
                </div>
                <form className="signup_form" onSubmit={handleSubmit} >
                    <FormBlock id="name" label="Name" placeholder="Ivan" type="text" required />
                    <FormBlock id="email" label="Email" placeholder="m@example.com" type="email" required />
                    <FormBlock id="username" label="Username" placeholder="username" type="text" required />
                    <FormBlock id="password" label="Password" type="password" placeholder="password" required onChange={(e) => setPassword(e.target.value)} />
                    <FormBlock id="confirm-password" label="Confirm Password" placeholder="confirm password" type="password" required onChange={(e) => setConfirmPassword(e.target.value)} />
                    <Button type="submit">
                        Register
                    </Button>
                </form>
                <div>
                    Already have an account?
                    <Link to="/login">
                        Sign In
                    </Link>
                </div>
            </div>
        </div>
    );
}
