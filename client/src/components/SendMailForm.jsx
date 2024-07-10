import { React, useState } from "react";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
  CardFooter,
} from "./Card";
import Label from "./Label";
import Input from "./Input";
import Textarea from "./TextArea";
import Button from "./Button";
import axios from "axios";
import { BASE_URL } from "../config";

export default function Component({ client, onClose }) {
  const [subject, setSubject] = useState("");
  const [text, setText] = useState("");

  async function handleSubmit() {
    axios
      .post(`${BASE_URL}:8080/mail/send`, {
        destination: client.email,
        subject: subject,
        body: text,
      })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.warn(error);
      });
  }

  return (
    <div
      className="fixed z-10 inset-0 overflow-y-auto"
      aria-labelledby="modal-title"
      role="dialog"
      aria-modal="true"
    >
      <div className="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
        <div
          className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"
          aria-hidden="true"
        ></div>
        <span
          className="hidden sm:inline-block sm:align-middle sm:h-screen"
          aria-hidden="true"
        >
          &#8203;
        </span>
        <div className="inline-block align-bottom bg-slate-700 rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
          <Card className="flex flex-col w-full max-w-md mx-auto justify-center py-2">
            <CardHeader>
              <CardTitle>Send Email</CardTitle>
              <CardDescription>
                Fill out the form below to send an email.
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="flex flex-col space-y-2 w-full">
                <Label htmlFor="subject">Subject</Label>
                <Input
                  id="subject"
                  placeholder="Email subject"
                  required
                  onChange={(e) => setSubject(e.target.value)}
                />
              </div>
              <div className="flex flex-col space-y-2 w-full">
                <Label htmlFor="body">Body</Label>
                <Textarea
                  id="body"
                  placeholder="Email body"
                  className="min-h-[150px]"
                  required
                  onChange={(e) => setText(e.target.value)}
                />
              </div>
            </CardContent>
            <CardFooter className="flex justify-center py-2 space-x-2">
              <Button
                type="submit"
                className="rounded-md"
                OnClick={handleSubmit}
              >
                Send Email
              </Button>
            </CardFooter>
          </Card>
          <button type="button" className="rounded-md" onClick={onClose}>
            Close
          </button>
        </div>
      </div>
    </div>
  );
}
