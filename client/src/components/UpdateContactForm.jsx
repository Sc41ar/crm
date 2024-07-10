import React, { useState } from "react";
import axios from "axios";
import Button from "../components/Button";
import Input from "../components/Input";
import { BASE_URL } from "../config";

const EditContactForm = ({ onClose, contactId }) => {
  const [field, setField] = useState("name");
  const [value, setValue] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    let url = `${BASE_URL}:8080/crm/client/update`;
    try {
      const data = { id: contactId, [field]: value };
      const response = await axios.put(url, data);
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
        <div className="inline-block align-bottom bg-slate-300 rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
          <div className="bg-slate-200 px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
            <div className="sm:flex sm:items-start">
              <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                <h3
                  className="text-lg leading-6 font-medium text-gray-900"
                  id="modal-title"
                >
                  Edit Contact
                </h3>
                <div className="mt-2">
                  <form
                    className="p-4 space-y-6 space-x-1"
                    onSubmit={handleSubmit}
                  >
                    <div className="flex items-center space-x-2">
                      <label>
                        <select
                          value={field}
                          onChange={(e) => setField(e.target.value)}
                          className="form-select text-slate-600 rounded-md w-28 h-10"
                        >
                          <option value="name">Имя</option>
                          <option value="lastName">Фамилия</option>
                          <option value="middleName">Отчество</option>
                          <option value="address">Адрес</option>
                          <option value="email">Почта</option>
                          <option value="phoneNumber">Номер</option>
                          <option value="description">Описание</option>
                        </select>
                      </label>
                      <Input
                        placeholder="Value"
                        value={value}
                        onChange={(e) => setValue(e.target.value)}
                      />
                    </div>
                    <Button
                      type="submit"
                      className="w-2/3 place-content-center place-self-stretch rounded-lg bg-indigo-800 hover:bg-violet-900 hover:border-rose-950 hover:border-2 hover:border-r-2"
                    >
                      Обновить
                    </Button>
                  </form>
                </div>
              </div>
            </div>
          </div>

          <div className="bg-slate-300 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
            <button
              type="button"
              className="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm"
              onClick={onClose}
            >
              Cancel
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditContactForm;
