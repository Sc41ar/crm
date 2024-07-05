import React from "react";

const Input = ({
  id,
  placeholder,
  required,
  type,
  onChange,
  className,
  step,
  min,
}) => {
  return (
    <input
      onChange={onChange}
      id={id}
      placeholder={placeholder}
      required={required}
      type={type}
      step={step}
      min={min}
      className={
        "rounded-md bg-indigo-300 dark:bg-indigo-900 hover:bg-purple-300 hover:dark:bg-purple-900 p-2 " +
        className
      }
    />
  );
};

export default Input;
