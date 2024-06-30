import { React, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Input from "../components/Input";
import Button from "../components/Button";
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuItem,
} from "../components/DropdownMenu";
import { Navigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent } from "../components/Card";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "../components/Table";

export default function Component() {
  const [costumersNumber, setCostumersNumber] = useState(0);

  const getClients = async () => {
    let personNumber = 0;
    try {
      await axios.get("http://localhost:8080/crm/client").then((response) => {
        personNumber = response.data.length;
      });
      await axios.get("http://localhost:8080/crm/company").then((response) => {
        setCostumersNumber(personNumber + response.data.length);
      });
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    const verifyUser = async () => {
      try {
        await axios
          .post(
            "http://localhost:8080/crm/verify",
            {},
            { withCredentials: true }
          )
          .then((response) => {
            console.log(response);
            if (response.status == axios.HttpStatusCode.Ok) {
            }
          });
      } catch (error) {
        console.error("Error verifying user:", error);
        window.location.href = "/login";
      }
    };

    verifyUser();
    getClients();
  }, []);

  return (
    <div className="flex justify-center h-screen w-screen">
      <nav className="flex flex-col bg-gray-900 text-gray-400 dark:bg-gray-950 dark:text-gray-400">
        <div className="flex h-16 justify-center border-b border-gray-800 dark:border-gray-800">
          <Link
            href="#"
            className="flex items-center gap-2 text-lg font-semibold"
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
              >
                <LayoutGridIcon className="h-5 w-5" />
                <span>Dashboard</span>
              </Link>
            </li>
            <li>
              <Link
                to="/contacts"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <UsersIcon className="h-5 w-5" />
                <span>Contacts</span>
              </Link>
            </li>
            <li>
              <Link
                to="/deals"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <BriefcaseIcon className="h-5 w-5" />
                <span>Deals</span>
              </Link>
            </li>
            <li>
              <Link
                to="#"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <SquareCheckIcon className="h-5 w-5" />
                <span>Tasks</span>
              </Link>
            </li>
            <li>
              <Link
                to="#"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <BarChart2Icon className="h-5 w-5" />
                <span>Reports</span>
              </Link>
            </li>
            <li>
              <Link
                to="#"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <SettingsIcon className="h-5 w-5" />
                <span>Settings</span>
              </Link>
            </li>
            <li>
              <Link
                to="#"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <LogoutIcon className="h-5 w-5" />
                <span>Logout</span>
              </Link>
            </li>
          </ul>
        </div>
      </nav>
      <main className="flex-1 bg-gray-500 dark:bg-gray-950">
        <header className="flex h-16 items-center justify-between border-b bg-white px-6 dark:border-gray-800 dark:bg-gray-950">
          <h1 className="text-lg font-medium">Dashboard</h1>
          <div className="flex items-center gap-4">
            <form className="flex items-center space-x-2">
              <SearchIcon className="h-5 w-5 text-gray-500 dark:text-gray-400" />
              <Input
                type="search"
                placeholder="Search..."
                className="flex-1 border-none bg-transparent text-sm px-2 focus:outline-none focus:ring-0"
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
          <div className="grid grid-cols-2 gap-4 sm:grid-cols-2 lg:grid-cols-4">
            <Card className="w-full bg-white dark:bg-gray-800 shadow rounded-lg p-4">
              <CardHeader className="flex flex-row items-center justify-between pb-2">
                <CardTitle className="text-sm font-medium">
                  Total Customers
                </CardTitle>
                <UsersIcon className="h-4 w-4 text-gray-500 dark:text-gray-400" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">{costumersNumber}</div>
                <p className="text-xs text-gray-500 dark:text-gray-400">
                  +0.0% from last month
                </p>
              </CardContent>
            </Card>
            <Card className="w-full bg-white dark:bg-gray-800 shadow rounded-lg p-4">
              <CardHeader className="flex flex-row items-center justify-between pb-2">
                <CardTitle className="text-sm font-medium">New Leads</CardTitle>
                <ActivityIcon className="h-4 w-4 text-gray-500 dark:text-gray-400" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">234</div>
                <p className="text-xs text-gray-500 dark:text-gray-400">
                  +12.8% from last month
                </p>
              </CardContent>
            </Card>
            <Card className="w-full bg-white dark:bg-gray-800 shadow rounded-lg p-4">
              <CardHeader className="flex flex-row items-center justify-between pb-2">
                <CardTitle className="text-sm font-medium">
                  Closed Deals
                </CardTitle>
                <BriefcaseIcon className="h-4 w-4 text-gray-500 dark:text-gray-400" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">78</div>
                <p className="text-xs text-gray-500 dark:text-gray-400">
                  +8.3% from last month
                </p>
              </CardContent>
            </Card>
            <Card className="w-full bg-white dark:bg-gray-800 shadow rounded-lg p-4">
              <CardHeader className="flex flex-row items-center justify-between pb-2">
                <CardTitle className="text-sm font-medium">
                  Open Tasks
                </CardTitle>
                <SquareCheckIcon className="h-4 w-4 text-gray-500 dark:text-gray-400" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold">142</div>
                <p className="text-xs text-gray-500 dark:text-gray-400">
                  +3.1% from last month
                </p>
              </CardContent>
            </Card>
          </div>
          <div className="mt-6">
            <Card className="w-full bg-white dark:bg-gray-800 shadow rounded-lg p-4">
              <CardHeader className="flex flex-row items-center justify-between pb-2 ">
                <CardTitle className="text-sm font-medium">
                  Recent Deals
                </CardTitle>
                <Link
                  href="#"
                  className="text-, font-medium text-blue-500 hover:underline"
                  prefetch={false}
                >
                  View all
                </Link>
              </CardHeader>
              <CardContent>
                <Table className="color-r-gray-50 dark:bg-gray-800 dark:border-gray-500 dark:text-gray-200">
                  <TableHeader>
                    <TableRow className="">
                      <TableHead className="font-semibold ">Deal</TableHead>
                      <TableHead className="font-semibold">Stage</TableHead>
                      <TableHead className="font-semibold">Amount</TableHead>
                      <TableHead className="font-semibold">
                        Close Date
                      </TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody className="container border-b border-gray-500 dark:border-gray-800">
                    <TableRow>
                      <TableCell className="font-medium">
                        <Link
                          href="#"
                          className="hover:underline"
                          prefetch={false}
                        >
                          Acme Inc. - Website Redesign
                        </Link>
                      </TableCell>
                      <TableCell>Negotiation</TableCell>
                      <TableCell>$15,000</TableCell>
                      <TableCell>June 30, 2023</TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell className="font-medium">
                        <Link
                          href="#"
                          className="hover:underline"
                          prefetch={false}
                        >
                          Globex Corp. - ERP Implementation
                        </Link>
                      </TableCell>
                      <TableCell>Proposal</TableCell>
                      <TableCell>$50,000</TableCell>
                      <TableCell>July 15, 2023</TableCell>
                    </TableRow>
                    <TableRow>
                      <TableCell className="font-medium">
                        <Link
                          href="#"
                          className="hover:underline"
                          prefetch={false}
                        >
                          Stark Industries - CRM Upgrade
                        </Link>
                      </TableCell>
                      <TableCell>Closed Won</TableCell>
                      <TableCell>$25,000</TableCell>
                      <TableCell>May 1, 2023</TableCell>
                    </TableRow>
                    <TableRow className="">
                      <TableCell className="font-medium">
                        <Link
                          href="#"
                          className="hover:underline"
                          prefetch={false}
                        >
                          Stark Industries - CRM Upgrade
                        </Link>
                      </TableCell>
                      <TableCell>Closed Won</TableCell>
                      <TableCell>$25,000</TableCell>
                      <TableCell>May 1, 2023</TableCell>
                    </TableRow>
                  </TableBody>
                </Table>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
}

function ActivityIcon(props) {
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
      <path d="M22 12h-2.48a2 2 0 0 0-1.93 1.46l-2.35 8.36a.25.25 0 0 1-.48 0L9.24 2.18a.25.25 0 0 0-.48 0l-2.35 8.36A2 2 0 0 1 4.49 12H2" />
    </svg>
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

function LogoutIcon(props) {
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
      <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
      <polyline points="16 17 21 12 16 7" />
      <line x1="21" x2="9" y1="12" y2="12" />
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
