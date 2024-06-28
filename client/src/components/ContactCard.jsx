import { React, useState } from "react";
import { Avatar, AvatarFallback, AvatarImage } from "./Avatar";
import Button from "./Button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "./DropdownMenu";
import { Card } from "./Card";
import ContactWindow from "./ContactWindow";

const ContactCard = ({ contact, ClassName }) => {
  const [showContactWindow, setShowContactWindow] = useState(false);
  const handleUserClick = () => {
    setShowContactWindow(true);
  };

  const handleCloseModal = () => {
    setShowContactWindow(false);
  };

  return (
    <Card className={ClassName}>
      <div className="flex items-center gap-1">
        <Avatar className="h-1/4 w-1/4 rounded-full">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            strokeWidth={1.5}
            stroke="currentColor"
            className="size-6"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z"
            />
          </svg>
          <AvatarFallback>{}</AvatarFallback>
        </Avatar>
        <div>
          <h3 className="text-lg font-medium">
            {contact.lastName + " " + contact.name}
          </h3>
          <p className="text-sm text-gray-500 dark:text-gray-400">
            {contact.phoneNumber}
          </p>
        </div>
      </div>
      <div className="mt-4 flex justify-between">
        <Button
          className="text-blue-500 hover:text-blue-700 hover:bg-blue-100 focus:ring-blue-500 focus:ring-2 rounded-xl"
          OnClick={handleUserClick}
        >
          View
        </Button>
        {showContactWindow && (
          <ContactWindow user={contact} onClose={handleCloseModal} />
        )}
        <Button className="text-blue-500 hover:text-blue-700 hover:bg-blue-100 focus:ring-blue-500 focus:ring-2 rounded-xl">
          Edit
        </Button>

        <DropdownMenu
          trigger={
            <Button
              variant="outline"
              size="sm"
              className="text-blue-500 hover:text-blue-700 hover:bg-blue-100 focus:ring-blue-500 focus:ring-2 rounded-xl"
            >
              <MoveVerticalIcon className="h-5 w-5" />
              <span className="sr-only">More options</span>
            </Button>
          }
        >
          <DropdownMenuContent align="end">
            <DropdownMenuItem>Send email</DropdownMenuItem>
            <DropdownMenuItem>Schedule call</DropdownMenuItem>
            <DropdownMenuItem>Add to deal</DropdownMenuItem>
            <DropdownMenuItem>Delete contact</DropdownMenuItem>
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
    </Card>
  );
};

function MoveVerticalIcon(props) {
  return (
    <svg
      {...props}
      xmlns="http://www.w3.org/2000/svg"
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth="2"
      strokeLinecap="round"
      strokeLinejoin="round"
    >
      <polyline points="8 18 12 22 16 18" />
      <polyline points="8 6 12 2 16 6" />
      <line x1="12" x2="12" y1="2" y2="22" />
    </svg>
  );
}

export default ContactCard;
