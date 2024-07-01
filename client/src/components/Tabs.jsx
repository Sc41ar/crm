import React, { useState } from "react";
import { Card } from "./Card";

export function Tabs({ children, className }) {
  const [activeTab, setActiveTab] = useState(children[0].props.label);

  return (
    <div className={className}>
      <TabsList>
        {children.map((child) => (
          <TabsTrigger
            key={child.props.label}
            value={child.props.label}
            onClick={setActiveTab}
          >
            {child.props.label}
          </TabsTrigger>
        ))}
      </TabsList>
      <TabsContent>
        {children.map((child) => {
          if (child.props.label === activeTab) {
            return <Card key={child.props.label}>{child.props.children}</Card>;
          }
          return null;
        })}
      </TabsContent>
    </div>
  );
}

export function Tab({ label, children, className }) {
  return (
    <TabsTrigger className={className} value={label}>
      {children}
    </TabsTrigger>
  );
}

export function TabsContent({ children, className }) {
  return (
    <div className={`p-4 bg-white rounded-b-lg  ${className}`}>{children}</div>
  );
}

export function TabsList({ children, className }) {
  return <div className={className}>{children}</div>;
}

export function TabsTrigger({ children, value, onClick }) {
  const [activeTab, setActiveTab] = useState(null);

  return (
    <button
      className={`py-2 px-4 rounded-t-lg ${
        value === activeTab
          ? "bg-blue-500 text-white"
          : "text-gray-500 hover:bg-gray-100"
      }`}
      onClick={() => {
        setActiveTab(value);
        onClick(value);
      }}
    >
      {children}
    </button>
  );
}
