import React, { useState } from "react";
import { Card } from "./Card";

export function Tabs({ children, className, defaultValue }) {
  const [activeTab, setActiveTab] = useState(defaultValue);

  return (
    <div className={className}>
      <TabsList>
        <TabsTrigger
          value="all"
          onClick={() => setActiveTab("all")}
          isActive={activeTab === "all"}
        >
          All
        </TabsTrigger>
        <TabsTrigger
          value="todo"
          onClick={() => setActiveTab("todo")}
          isActive={activeTab === "todo"}
        >
          To-Do
        </TabsTrigger>
        <TabsTrigger
          value="inProgress"
          onClick={() => setActiveTab("inProgress")}
          isActive={activeTab === "inProgress"}
        >
          In Progress
        </TabsTrigger>
        <TabsTrigger
          value="completed"
          onClick={() => setActiveTab("completed")}
          isActive={activeTab === "completed"}
        >
          Completed
        </TabsTrigger>
      </TabsList>
      <TabsContent>
        {children.map((child) => {
          if (child.type === TabsContent && child.props.value === activeTab) {
            return (
              <TabsContent
                key={child.props.value}
                className={child.props.className}
              >
                {child.props.children}
              </TabsContent>
            );
          }
          return null;
        })}
      </TabsContent>
    </div>
  );
}

export function Tab({ label, children, className }) {
  return (
    <TabsTrigger
      key={child.props.label}
      onClick={() => setActiveTab(child.props.label)}
      className={className}
      value={label}
    >
      {children}
    </TabsTrigger>
  );
}

export function TabsContent({ children, className, value }) {
  return (
    <div className={`p-4 bg-white rounded-b-lg ${className}`}>{children}</div>
  );
}

export function TabsList({ children, className }) {
  return <div className={className}>{children}</div>;
}

export function TabsTrigger({ children, value, onClick, isActive }) {
  return (
    <button
      className={`py-2 px-4 rounded-t-lg ${
        isActive ? "bg-blue-500 text-white" : "text-gray-500"
      }`}
      onClick={onClick}
    >
      {children}
    </button>
  );
}
