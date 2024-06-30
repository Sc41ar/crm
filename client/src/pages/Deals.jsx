import React from "react";
import { Link } from "react-router-dom";
import Input from "../components/Input";
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuItem,
} from "../components/DropdownMenu";
import Button from "../components/Button";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
  CardFooter,
} from "../components/Card";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "../components/Table";
import Badge from "../components/Badge";

export default function Component() {
  return (
    <div className="flex h-screen w-screen">
      <nav className="flex flex-col bg-gray-900 text-gray-400 dark:bg-gray-950 dark:text-gray-400">
        <div className="flex h-16 items-center justify-center border-b border-gray-800 dark:border-gray-800">
          <Link
            href="#"
            className="flex items-center gap-2 text-lg font-semibold"
            prefetch={false}
          >
            <Package2Icon className="h-6 w-6" />
            <span className="sr-only">CRM</span>
          </Link>
        </div>
        <div className="flex-1 py-4">
          <ul className="grid gap-1">
            <li>
              <Link
                to="/"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
                prefetch={false}
              >
                <LayoutGridIcon className="h-5 w-5" />
                <span>Dashboard</span>
              </Link>
            </li>
            <li>
              <Link
                to="/contacts"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
                prefetch={false}
              >
                <UsersIcon className="h-5 w-5" />
                <span>Contacts</span>
              </Link>
            </li>
            <li>
              <Link
                to="/deals"
                className="flex items-center gap-3 rounded-lg px-4 py-2 bg-gray-800 text-gray-50 dark:bg-gray-800 dark:text-gray-50"
                prefetch={false}
              >
                <BriefcaseIcon className="h-5 w-5" />
                <span>Deals</span>
              </Link>
            </li>
            <li>
              <Link
                href="#"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
                prefetch={false}
              >
                <SquareCheckIcon className="h-5 w-5" />
                <span>Tasks</span>
              </Link>
            </li>
            <li>
              <Link
                href="#"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
                prefetch={false}
              >
                <BarChart2Icon className="h-5 w-5" />
                <span>Reports</span>
              </Link>
            </li>
            <li>
              <Link
                href="#"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
                prefetch={false}
              >
                <SettingsIcon className="h-5 w-5" />
                <span>Settings</span>
              </Link>
            </li>
          </ul>
        </div>
      </nav>
      <main className="flex-1 bg-gray-100 dark:bg-gray-950">
        <header className="flex h-16 items-center justify-between border-b bg-white px-6 dark:border-gray-800 dark:bg-gray-950">
          <h1 className="text-lg font-medium">Deals</h1>
          <div className="flex items-center gap-4">
            <form className="relative">
              <SearchIcon className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-500 dark:text-gray-400" />
              <Input
                type="search"
                placeholder="Search deals..."
                className="pl-8 pr-4 h-9 rounded-md bg-gray-100 dark:bg-gray-800 dark:text-gray-50"
              />
            </form>
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon" className="rounded-full">
                  <img
                    src="/placeholder.svg"
                    width="32"
                    height="32"
                    className="rounded-full"
                    alt="Avatar"
                  />
                  <span className="sr-only">Toggle user menu</span>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuLabel>John Doe</DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuItem>Profile</DropdownMenuItem>
                <DropdownMenuItem>Settings</DropdownMenuItem>
                <DropdownMenuItem>Logout</DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        </header>
        <div className="p-6">
          <Card className="w-full bg-white dark:bg-gray-800 shadow rounded-lg p-4">
            <CardHeader>
              <CardTitle>Deals</CardTitle>
              <CardDescription>
                Manage your deals and view their status.
              </CardDescription>
            </CardHeader>
            <CardContent>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Deal Name</TableHead>
                    <TableHead>Stage</TableHead>
                    <TableHead>Amount</TableHead>
                    <TableHead>Close Date</TableHead>
                    <TableHead>Actions</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody className="w-full bg-white dark:bg-gray-600 shadow rounded-lg p-4">
                  <TableRow className="w-full bg-white dark:bg-slate-600 shadow rounded-lg p-4 text-center ">
                    <TableCell className="font-medium">Project X</TableCell>
                    <TableCell>
                      <Badge
                        variant="danger"
                        className="w-full bg-white dark:bg-slate-300 rounded-md p-4"
                      >
                        Negotiation
                      </Badge>
                    </TableCell>
                    <TableCell className="">$50,000</TableCell>
                    <TableCell className="">2023-07-31</TableCell>
                    <TableCell className="">
                      <div className="flex gap-3 justify-center">
                        <Button
                          className="text-blue-500 hover:text-blue-700 hover:bg-blue-100 focus:ring-blue-500 focus:ring-2 rounded-xl"
                          variant="outline"
                        >
                          View
                        </Button>
                        <Button
                          className="text-blue-500 hover:text-blue-700 hover:bg-blue-100 focus:ring-blue-500 focus:ring-2 rounded-xl"
                          variant="outline"
                        >
                          Edit
                        </Button>
                        <DropdownMenu
                          trigger={
                            <Button
                              className="text-blue-500 hover:text-blue-700 hover:bg-blue-100 focus:ring-blue-500 focus:ring-2 rounded-xl"
                              variant="outline"
                            >
                              <MoveVerticalIcon className="h-5.5 w-5.5" />
                              <span className="sr-only">More options</span>
                            </Button>
                          }
                        >
                          <DropdownMenuContent align="end">
                            <DropdownMenuItem>Mark as won</DropdownMenuItem>
                            <DropdownMenuItem>Mark as lost</DropdownMenuItem>
                            <DropdownMenuItem>Add to calendar</DropdownMenuItem>
                            <DropdownMenuItem>Delete deal</DropdownMenuItem>
                          </DropdownMenuContent>
                        </DropdownMenu>
                      </div>
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
            </CardContent>
            <CardFooter>
              <div className="text-xs text-gray-500 dark:text-gray-400">
                Showing <strong>1-5</strong> of <strong>10</strong> deals
              </div>
            </CardFooter>
          </Card>
        </div>
      </main>
    </div>
  );
}

function BarChart2Icon(props) {
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
      <line x1="18" x2="18" y1="20" y2="10" />
      <line x1="12" x2="12" y1="20" y2="4" />
      <line x1="6" x2="6" y1="20" y2="14" />
    </svg>
  );
}

function BriefcaseIcon(props) {
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
      <path d="M16 20V4a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16" />
      <rect width="20" height="14" x="2" y="6" rx="2" />
    </svg>
  );
}

function LayoutGridIcon(props) {
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
      <rect width="7" height="7" x="3" y="3" rx="1" />
      <rect width="7" height="7" x="14" y="3" rx="1" />
      <rect width="7" height="7" x="14" y="14" rx="1" />
      <rect width="7" height="7" x="3" y="14" rx="1" />
    </svg>
  );
}

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

function Package2Icon(props) {
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
      <path d="M3 9h18v10a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V9Z" />
      <path d="m3 9 2.45-4.9A2 2 0 0 1 7.24 3h9.52a2 2 0 0 1 1.8 1.1L21 9" />
      <path d="M12 3v6" />
    </svg>
  );
}

function SearchIcon(props) {
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
      <circle cx="11" cy="11" r="8" />
      <path d="m21 21-4.3-4.3" />
    </svg>
  );
}

function SettingsIcon(props) {
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
      <path d="M12.22 2h-.44a2 2 0 0 0-2 2v.18a2 2 0 0 1-1 1.73l-.43.25a2 2 0 0 1-2 0l-.15-.08a2 2 0 0 0-2.73.73l-.22.38a2 2 0 0 0 .73 2.73l.15.1a2 2 0 0 1 1 1.72v.51a2 2 0 0 1-1 1.74l-.15.09a2 2 0 0 0-.73 2.73l.22.38a2 2 0 0 0 2.73.73l.15-.08a2 2 0 0 1 2 0l.43.25a2 2 0 0 1 1 1.73V20a2 2 0 0 0 2 2h.44a2 2 0 0 0 2-2v-.18a2 2 0 0 1 1-1.73l.43-.25a2 2 0 0 1 2 0l.15.08a2 2 0 0 0 2.73-.73l.22-.39a2 2 0 0 0-.73-2.73l-.15-.08a2 2 0 0 1-1-1.74v-.5a2 2 0 0 1 1-1.74l.15-.09a2 2 0 0 0 .73-2.73l-.22-.38a2 2 0 0 0-2.73-.73l-.15.08a2 2 0 0 1-2 0l-.43-.25a2 2 0 0 1-1-1.73V4a2 2 0 0 0-2-2z" />
      <circle cx="12" cy="12" r="3" />
    </svg>
  );
}

function SquareCheckIcon(props) {
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
      <rect width="18" height="18" x="3" y="3" rx="2" />
      <path d="m9 12 2 2 4-4" />
    </svg>
  );
}

function UsersIcon(props) {
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
      <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
      <circle cx="9" cy="7" r="4" />
      <path d="M22 21v-2a4 4 0 0 0-3-3.87" />
      <path d="M16 3.13a4 4 0 0 1 0 7.75" />
    </svg>
  );
}
