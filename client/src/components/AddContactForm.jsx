import React, { useState } from "react";
import axios from "axios";
import Button from "../components/Button";
import Input from "../components/Input";

const AddContactForm = ({ onClose }) => {
  const [option, setOption] = useState("person");
  const [name, setName] = useState("");
  const [lastName, setLastName] = useState("");
  const [middleName, setMiddleName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [description, setDescription] = useState("");
  const [companyName, setCompanyName] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    const url = "http://localhost:8080/crm/client/add";
    try {
      const data = {
        email,
        phone,
        address,
        description,
      };
      if (option === "person") {
        data.name = name;
        data.lastName = lastName;
        data.middleName = middleName;
        data.companyName = companyName;
      } else {
        url = "http://localhost:8080/crm/company/add";
        data.name = companyName;
      }
      const response = await axios.post(url, data);
      console.log(response);
      onClose();
    } catch (error) {
      console.error(error);
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
        <div className="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
          <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
            <div className="sm:flex sm:items-start">
              <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                <h3
                  className="text-lg leading-6 font-medium text-gray-900"
                  id="modal-title"
                >
                  Add Contact
                </h3>
                <div className="mt-2">
                  <form className="p-4 space-y-4" onSubmit={handleSubmit}>
                    <div className="flex items-center space-x-2">
                      <label className="inline-flex items-center">
                        <input
                          type="radio"
                          name="option"
                          value="person"
                          checked={option === "person"}
                          onChange={(e) => setOption(e.target.value)}
                          className="form-radio text-blue-500"
                        />
                        <span className="ml-2">Person</span>
                      </label>
                      <label className="inline-flex items-center">
                        <input
                          type="radio"
                          name="option"
                          value="company"
                          checked={option === "company"}
                          onChange={(e) => setOption(e.target.value)}
                          className="form-radio text-blue-500"
                        />
                        <span className="ml-2">Company</span>
                      </label>
                    </div>
                    {option === "person" ? (
                      <>
                        <Input
                          type="text"
                          placeholder="Name"
                          value={name}
                          onChange={(e) => setName(e.target.value)}
                        />
                        <Input
                          type="text"
                          placeholder="Last Name"
                          value={lastName}
                          onChange={(e) => setLastName(e.target.value)}
                        />
                        <Input
                          type="text"
                          placeholder="Middle Name"
                          value={middleName}
                          onChange={(e) => setMiddleName(e.target.value)}
                        />
                        <Input
                          type="text"
                          placeholder="Company Name"
                          value={companyName}
                          onChange={(e) => setCompanyName(e.target.value)}
                        />
                      </>
                    ) : (
                      <Input
                        type="text"
                        placeholder="Company Name"
                        value={companyName}
                        onChange={(e) => setCompanyName(e.target.value)}
                      />
                    )}
                    <Input
                      type="email"
                      placeholder="Email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                    />
                    <Input
                      type="tel"
                      placeholder="Phone"
                      value={phone}
                      onChange={(e) => setPhone(e.target.value)}
                    />
                    <Input
                      type="text"
                      placeholder="Address"
                      value={address}
                      onChange={(e) => setAddress(e.target.value)}
                    />
                    <Input
                      type="text"
                      placeholder="Description"
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                    />
                    <Button type="submit">Create Contact</Button>
                  </form>
                </div>
              </div>
            </div>
          </div>
          <div className="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
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

export default AddContactForm;
