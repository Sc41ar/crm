import React from "react";

const Textarea = ({ id, placeholder, className, required, onChange }) => {
  return (
    <textarea
      id={id}
      placeholder={placeholder}
      className={className}
      required={required}
      onChange={onChange}
    />
  );
};

export default Textarea;
