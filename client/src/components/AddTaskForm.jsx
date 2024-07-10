import React, { useState } from "react";
import axios from "axios";
import Button from "../components/Button";
import Input from "../components/Input";
import { format } from "date-fns";
import ReactDatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { BASE_URL } from "../config";

const AddTaskForm = ({ onClose }) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("TODO");
  const [deadline, setDeadline] = useState("");
  const [expiresAt, setExpiresAt] = useState(null);
  const [createdAt, setCreatedAt] = useState(new Date().toISOString());

  const handleSubmit = async (e) => {
    e.preventDefault();
    let url = `${BASE_URL}:8080/task/add`;

    try {
      let data = {
        name: name,
        description: description,
        status: status,
        username: sessionStorage.getItem("loginInfo"),
        createdAt: createdAt,
        deadline: deadline,
        expiresAt: expiresAt,
      };
      console.log(data);
      const response = await axios.post(url, data);
      console.log(response);

      onClose();
    } catch (error) {
      alert(error.response.data.error);
    }
  };

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
        <div className="inline-block align-bottom bg-slate-300 rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-xl sm:w-full">
          {" "}
          <div className="bg-slate-200 px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
            <div className="sm:flex sm:items-start">
              <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                <h3
                  className="text-lg leading-6 font-medium text-gray-900"
                  id="modal-title"
                >
                  Add Contact
                </h3>
                <div className="mt-2">
                  <form
                    className=" flex flex-col p-4 space-y-6 space-x-1"
                    onSubmit={handleSubmit}
                  >
                    <div className="flex items-center space-x-2">
                      <label className="inline-flex items-center">
                        <input
                          type="radio"
                          name="status"
                          value="TODO"
                          checked={status === "TODO"}
                          onChange={(e) => setStatus(e.target.value)}
                          className="form-radio text-blue-500"
                        />
                        <span className="ml-2 text-slate-600">TODO</span>
                      </label>
                      <label className="inline-flex items-center">
                        <input
                          type="radio"
                          name="status"
                          value="IN_PROGRESS"
                          checked={status === "IN_PROGRESS"}
                          onChange={(e) => setStatus(e.target.value)}
                          className="form-radio text-blue-500"
                        />
                        <span className="ml-2 text-slate-600">IN PROGRESS</span>
                      </label>
                    </div>

                    <Input
                      placeholder="Name"
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                      required
                    />
                    <Input
                      placeholder="Description"
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                      required
                    />
                    <ReactDatePicker
                      selected={expiresAt}
                      onChange={(date) => setExpiresAt(date)}
                      showTimeSelect
                      dateFormat="yyyy-MM-dd HH:mm"
                      placeholderText="Expires"
                      className="rounded-md bg-indigo-300 dark:bg-indigo-900 hover:bg-purple-300 hover:dark:bg-purple-900 p-2"
                    />
                    <ReactDatePicker
                      selected={deadline}
                      onChange={(date) => setDeadline(date)}
                      showTimeSelect
                      dateFormat="yyyy-MM-dd HH:mm"
                      placeholderText="Deadline"
                      required
                      className="rounded-md  bg-indigo-300 dark:bg-indigo-900 hover:bg-purple-300 hover:dark:bg-purple-900 p-2"
                    />
                    <Button
                      type="submit"
                      className="w-2/3 place-content-center place-self-stretch rounded-lg bg-indigo-800 hover:bg-violet-900 hover:border-rose-950 hover:border-2 hover:border-r-2"
                    >
                      Create Task
                    </Button>
                  </form>
                </div>
              </div>
            </div>
          </div>
          <div className="bg-slate-300 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
            <button
              type="button"
              className="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-gray-600 text-base font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500 sm:ml-3 sm:w-auto sm:text-sm"
              onClick={onClose}
            >
              Close
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddTaskForm;
