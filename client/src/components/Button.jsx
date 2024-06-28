import React from "react";

const Button = ({ className, type, children, OnClick }) => {
  return (
    <button className={className} type={type} onClick={OnClick}>
      {children}
    </button>
  );
};

export default Button;
