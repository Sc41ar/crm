import React from 'react';


export  function Card({ children }) {
  return (
    <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-4">
      {children}
    </div>
  );
}

export function CardHeader({ children, className }) {
  return <div className={className}>{children}</div>;
}

export function CardTitle({ children, className }) {
  return <h3 className={className}>{children}</h3>;
}

export function CardContent({ children }) {
  return <div>{children}</div>;
}
