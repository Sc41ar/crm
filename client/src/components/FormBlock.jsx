import React from "react";

const FormBlock = ({ label, id, placeholder, type, required, className }) => {
  return (
    <div className={className}>
      <label htmlFor={id}>{label}</label>
      <input
        id={id}
        placeholder={placeholder}
        required={required}
        type={type}
        className="border rounded-md p-2"
      />
    </div>
  );
};
export default FormBlock;
