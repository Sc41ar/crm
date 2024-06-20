import React from 'react';

const Input = ({ id, placeholder, required, type }) => {
  return <input id={id} placeholder={placeholder} required={required} type={type} />;
};

export default Input;
