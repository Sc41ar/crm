import { React, useState, useEffect } from "react";
import axios from "axios";
import Button from "../components/Button";
import Input from "../components/Input";
import { format } from "date-fns";
import ReactDatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const AddDealForm = ({ client, onClose }) => {
  const [name, setName] = useState("");
  const [stage, setStage] = useState("OPEN");
  const [totalCost, setTotalCost] = useState(0);
  const [type, setType] = useState("");
  const [endDate, setEndDate] = useState(null);
  const [startDate, setStartDate] = useState(null);
  const [products, setProducts] = useState([]);
  const [quantity, setQuantity] = useState(0);
  const [unit, setUnit] = useState("");
  const [selectedProductId, setSelectedProductId] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    let url = "http://localhost:8080/crm/deal/add";

    try {
      let data = {
        name: name,
        stage: stage,
        type: type,
        userUsername: sessionStorage.getItem("loginInfo"),
        clientId: client.id,
        startDate: startDate,
        endDate: endDate,
      };
      const response = await axios.post(url, data);
      let responseDealDto = response.data;
      console.log(unit);
      data = {
        dealId: responseDealDto.id,
        dealName: responseDealDto.name,
        productId: selectedProductId,
        unit: products.find((product) => product.id == selectedProductId)?.unit,
        productName: products.find((product) => product.id == selectedProductId)
          ?.name,
        quantity: quantity,
      };
      console.log(data);
      const responseS = await axios.post(
        "http://localhost:8080/crm/product-deal/add",
        data
      );
      console.log(responseS.data);
      onClose();
    } catch (error) {
      alert(error.response.data.error);
    }
  };

  useEffect(() => {
    getProducts();
  }, []);

  const getProducts = async () => {
    try {
      const response = await axios.get("http://localhost:8080/crm/product");
      setProducts(response.data);
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
                  Create deal
                </h3>
                <div className="mt-2">
                  <form
                    className="flex flex-col p-4 space-y-6 space-x-1"
                    onSubmit={handleSubmit}
                  >
                    <div className="relative p-4">
                      <select
                        value={selectedProductId}
                        onChange={(e) => {
                          setSelectedProductId(e.target.value);
                        }}
                        className="block appearance-none w-full bg-slate-700 border border-gray-400 hover:border-gray-500 px-4 py-2 pr-8 rounded shadow leading-tight focus:outline-none focus:shadow-outline"
                      >
                        {products.map((product) => (
                          <option key={product.id} value={product.id}>
                            {`${product.name}, ${product.unit}, ${product.unitPrice}`}
                          </option>
                        ))}
                      </select>
                      <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                        <svg
                          className="fill-current h-4 w-4"
                          xmlns="http://www.w3.org/2000/svg"
                          viewBox="0 0 20 20"
                        >
                          <path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z" />
                        </svg>
                      </div>

                      <select
                        value={stage}
                        onChange={(e) => {
                          console.log();
                          setStage(e.target.value);
                        }}
                        className="block appearance-none w-full bg-slate-700 border border-gray-400 hover:border-gray-500 px-4 py-2 pr-8 rounded shadow leading-tight focus:outline-none focus:shadow-outline"
                      >
                        <option value="OPEN">Open</option>
                        <option value="OFFER">Offer</option>
                        <option value="PREPARATION">
                          Preparation of documents
                        </option>
                        <option value="PREPAYMENT">Prepayment invoice</option>
                        <option value="WORK">At work</option>
                        <option value="INVOICE">Invoice</option>
                        <option value="WON">Won</option>
                        <option value="LOST">Lost</option>
                      </select>

                      <div className="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                        <svg
                          className="fill-current h-4 w-4"
                          xmlns="http://www.w3.org/2000/svg"
                          viewBox="0 0 20 20"
                        >
                          <path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z" />
                        </svg>
                      </div>
                    </div>

                    <Input
                      placeholder="Name"
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                      required
                    />
                    <Input
                      placeholder="Type"
                      value={type}
                      onChange={(e) => setType(e.target.value)}
                      required
                    />
                    <Input
                      type="number"
                      min="0"
                      step="0.01"
                      placeholder="Величина "
                      value={quantity}
                      onChange={(e) => {
                        const value = parseFloat(e.target.value);
                        if (value >= 0) setQuantity(value);
                      }}
                      required
                    />
                    <ReactDatePicker
                      selected={startDate}
                      onChange={(date) => setStartDate(date)}
                      showTimeSelect
                      dateFormat="yyyy-MM-dd HH:mm"
                      placeholderText="Start Date"
                      required
                      className="rounded-md bg-indigo-300 dark:bg-indigo-900 hover:bg-purple-300 hover:dark:bg-purple-900 p-2"
                    />
                    <ReactDatePicker
                      selected={endDate}
                      onChange={(date) => setEndDate(date)}
                      showTimeSelect
                      dateFormat="yyyy-MM-dd HH:mm"
                      placeholderText="End Date"
                      className="rounded-md  bg-indigo-300 dark:bg-indigo-900 hover:bg-purple-300 hover:dark:bg-purple-900 p-2"
                    />
                    <Button
                      type="submit"
                      className="w-2/3 place-content-center place-self-stretch rounded-lg bg-indigo-800 hover:bg-violet-900 hover:border-rose-950 hover:border-2 hover:border-r-2"
                    >
                      Create Deal
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

export default AddDealForm;
