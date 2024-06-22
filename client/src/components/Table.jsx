import React from 'react';

export function Table({ children }) {
    return (
      <div className="overflow-x-">
        <table className="w-full">
          {children}
        </table>
      </div>
    );
}
  
export function TableHeader({ children }) {
    return (
            <thead className="w-full text-sm font-semibold uppercase text-gray-400">
        {children}
      </thead>
    );
}
  
export function TableRow({ className, children }) {
    return <tr className={className}>{children}</tr>;
}
  
export function TableHead({ children, className }) {
    return <th  className={className}>{children}</th>;
} 
  
export function TableBody({ children }) {
    return <tbody>{children}</tbody>;
}
  
export function TableCell({ children, className }) {
    return <td className={className}>{children}</td>;
}

