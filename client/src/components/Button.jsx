import React from 'react';

const Button = ({ className, type, children }) => {
  return (
    <button className={className} type={type}>
      {children}
    </button>
  );
};

export default Button;
