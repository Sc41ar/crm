import React from "react";

export default function DropdownMenu({ children }) {
    const [isOpen, setIsOpen] = useState(false);
  
    const handleToggle = () => {
      setIsOpen(!isOpen);
    };
  
    const handleClose = () => {
      setIsOpen(false);
    };
  
    return (
      <div className="relative">
        <DropdownMenuTrigger onToggle={handleToggle} />
        {isOpen && (
          <DropdownMenuContent onClose={handleClose}>{children}</DropdownMenuContent>
        )}
      </div>
    );
  }
  
 export default function DropdownMenuTrigger({ children, onToggle }) {
    return (
      <div onClick={onToggle}>
        {React.cloneElement(children, {
          'aria-expanded': true,
          'aria-haspopup': true,
        })}
      </div>
    );
  }
  
  export default function DropdownMenuContent({ children, onClose }) {
    return (
      <div
        className="absolute right-0 mt-2 w-48 rounded-md shadow-lg bg-white dark:bg-gray-800 ring-1 ring-black ring-opacity-5 z-10"
        onMouseLeave={onClose}
      >
        <div className="py-1" role="menu" aria-orientation="vertical" aria-labelledby="options-menu">
          {children}
        </div>
      </div>
    );
  }
  
  function DropdownMenuLabel({ children }) {
    return <div className="block px-4 py-2 text-xs text-gray-500 dark:text-gray-400">{children}</div>;
  }
  
  function DropdownMenuSeparator() {
    return <div className="border-t border-gray-200 dark:border-gray-700" />;
  }
  
  function DropdownMenuItem({ children }) {
    return (
      <a
        href="#"
        className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-700"
        role="menuitem"
      >
        {children}
      </a>
    );
  }