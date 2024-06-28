import React from "react";

const Avatar = ({ className, children }) => {
  return <div className={`avatar ${className}`}>{children}</div>;
};

const AvatarImage = ({ src }) => {
  return <img className="avatar-image" src={src} alt="Avatar" />;
};

const AvatarFallback = ({ children }) => {
  return <div className="avatar-fallback">{children}</div>;
};

export { Avatar, AvatarImage, AvatarFallback };
