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
import UpdateContactForm from "./UpdateContactForm";

const ContactCard = ({ contact, ClassName }) => {
  const [showUpdateForm, setShowUpdateForm] = useState(false);
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
          {contact.lastName ? (
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
          ) : (
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
                d="M2.25 21h19.5m-18-18v18m10.5-18v18m6-13.5V21M6.75 6.75h.75m-.75 3h.75m-.75 3h.75m3-6h.75m-.75 3h.75m-.75 3h.75M6.75 21v-3.375c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21M3 3h12m-.75 4.5H21m-3.75 3.75h.008v.008h-.008v-.008Zm0 3h.008v.008h-.008v-.008Zm0 3h.008v.008h-.008v-.008Z"
              />
            </svg>
          )}
        </Avatar>
        <div>
          <h3 className="text-lg font-medium">
            {contact.lastName ? contact.lastName + " " : ""} {contact.name}
          </h3>
          <p className="text-sm text-gray-500 dark:text-gray-400">
            {contact.description}
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
        <Button
          className="text-blue-500 hover:text-blue-700 hover:bg-blue-100 focus:ring-blue-500 focus:ring-2 rounded-xl"
          OnClick={() => {
            setShowUpdateForm(true);
          }}
        >
          Edit
        </Button>
        {showUpdateForm && (
          <UpdateContactForm
            contactId={contact.id}
            onClose={() => {
              setShowUpdateForm(false);
            }}
          />
        )}

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
