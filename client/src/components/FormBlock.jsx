import React from 'react';

const FormBlock = ({ label, id, placeholder, type, required }) => {
  return (
    <div className="form_block">
      <label htmlFor={id}>{label}</label>
      <input id={id} placeholder={placeholder} required={required} type={type} className="border rounded p-2" />
    </div>
  );
}
export default FormBlock;
