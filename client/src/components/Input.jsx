import React from "react";

const Input = ({ id, placeholder, required, type, onChange }) => {
  return (
    <input
      onChange={onChange}
      id={id}
      placeholder={placeholder}
      required={required}
      type={type}
      className="rounded-md bg-indigo-300 dark:bg-indigo-900 hover:bg-purple-300 hover:dark:bg-purple-900 p-2"
    />
  );
};

export default Input;
