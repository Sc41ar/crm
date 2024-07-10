import React, { useState } from "react";
import axios from "axios";
import Button from "../components/Button";
import Input from "../components/Input";
import { BASE_URL } from "../config";

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
  const [emailError, setEmailError] = useState(false);
  const [phoneError, setPhoneError] = useState(false);

  const checkEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9.-_]+@([a-zA-Z0-9]+\.)+[a-zA-Z0-9-]+$/;
    return emailRegex.test(email);
  };

  const checkPhone = (phone) => {
    const phoneRegex = /^\+7\d{10}$/;
    return phoneRegex.test(phone);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    let url = `${BASE_URL}:8080/crm/client/add`;

    if (!checkEmail(email) || !checkPhone(phone)) {
      setEmailError(true);
      setPhoneError(true);
      return;
    }
    setEmailError(false);
    setPhoneError(false);

    try {
      let data = {
        name: name,
        email: email,
        phoneNumber: phone,
        address: address,
        description: description,
      };
      if (option === "person") {
        data.name = name;
        data.lastName = lastName;
        data.middleName = middleName;
        data.companyName = companyName;
      } else {
        url = `${BASE_URL}:8080/crm/company/add`;
        data.name = companyName;
      }
      const response = await axios.post(url, data);
      console.log(response);

      onClose();
    } catch (error) {
      if (error.response.data.error === "Не найдена запись о компании") {
        alert("Записи о данной компании не существует, создайте запись");
        setOption("company");
        forceUpdate();
      }
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
        <div className="inline-block align-bottom bg-slate-300 rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
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
                    className="p-4 space-y-6 space-x-1"
                    onSubmit={handleSubmit}
                  >
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
                        <span className="ml-2 text-slate-600">Person</span>
                      </label>
                      <label className="inline-flex items-center  ">
                        <input
                          type="radio"
                          name="option"
                          value="company"
                          checked={option === "company"}
                          onChange={(e) => setOption(e.target.value)}
                          className="form-radio text-blue-500"
                        />
                        <span className="ml-2 text-slate-600">Company</span>
                      </label>
                    </div>
                    {option === "person" ? (
                      <>
                        <Input
                          placeholder="Name"
                          value={name}
                          onChange={(e) => {
                            setName(e.target.value);
                          }}
                        />
                        <Input
                          placeholder="Last Name"
                          value={lastName}
                          onChange={(e) => setLastName(e.target.value)}
                        />
                        <Input
                          placeholder="Middle Name"
                          value={middleName}
                          onChange={(e) => setMiddleName(e.target.value)}
                        />
                        <Input
                          placeholder="Company Name"
                          value={companyName}
                          onChange={(e) => setCompanyName(e.target.value)}
                        />
                      </>
                    ) : (
                      <Input
                        placeholder="Company Name"
                        value={companyName}
                        onChange={(e) => setCompanyName(e.target.value)}
                      />
                    )}
                    <Input
                      placeholder="Email"
                      value={email}
                      onChange={(e) => {
                        setEmail(e.target.value);
                      }}
                      className={
                        emailError
                          ? "border-red-500 hover:dark:bg-red-900 border-spacing-5 border-2"
                          : " "
                      }
                    />
                    <Input
                      placeholder="Phone"
                      value={phone}
                      onChange={(e) => setPhone(e.target.value)}
                      className={
                        phoneError
                          ? "border-red-500 hover:dark:bg-red-900 border-spacing-5 border-2"
                          : " "
                      }
                    />
                    <Input
                      placeholder="Address"
                      value={address}
                      onChange={(e) => setAddress(e.target.value)}
                    />
                    <Input
                      placeholder="Description"
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                    />
                    <Button
                      type="submit"
                      className="w-2/3 place-content-center place-self-stretch rounded-lg bg-indigo-800 hover:bg-violet-900 hover:border-rose-950 hover:border-2 hover:border-r-2"
                    >
                      Create Contact
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

export default AddContactForm;
