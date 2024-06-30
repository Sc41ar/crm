import React from "react";

const Badge = ({ text, variant, children, className }) => {
  let badgeClass = "px-2 py-1 ";

  switch (variant) {
    case "primary":
      badgeClass += "bg-blue-100 text-blue-800";
      break;
    case "secondary":
      badgeClass += "bg-green-100 text-green-800";
      break;
    case "danger":
      badgeClass += "bg-red-100 text-red-800";
      break;
    default:
      badgeClass += "bg-gray-100 text-gray-800";
  }

  return <span className={`${badgeClass} ${className}`}>{children}</span>;
};

export default Badge;
