import React from "react";

export function Card({ children, className }) {
  return <div className={className} /*""*/>{children}</div>;
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

export function CardDescription({ text }) {
  return <p className="text-sm text-gray-500 dark:text-gray-400">{text}</p>;
}

export function CardFooter({ children, className }) {
  return <div className={className}>{children}</div>;
}
