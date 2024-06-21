import React from 'react';

export function Table({ children }) {
    return (
      <div className="overflow-x-auto">
        <table className="table-auto w-full">
          {children}
        </table>
      </div>
    );
}
  
export function TableHeader({ children }) {
    return (
      <thead className="text-xs font-semibold uppercase text-gray-400 bg-gray-50">
        {children}
      </thead>
    );
}
  
export function TableRow({ children }) {
    return <tr>{children}</tr>;
}
  
export function TableHead({ children }) {
    return <th>{children}</th>;
}
  
export function TableBody({ children }) {
    return <tbody>{children}</tbody>;
}
  
export function TableCell({ children, className }) {
    return <td className={className}>{children}</td>;
}

