import { React, useState, useEffect } from "react";
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
import axios from "axios";

import AddTaskForm from "../components/AddTaskForm";
import { BASE_URL } from "../config";

export default function Component() {
  const [tasks, setTasks] = useState([]);
  const [selectedButton, setSelectedButton] = useState("All Tasks");
  const [isAddTaskFormVisible, setIsAddTaskFormVisible] = useState(false);
  const [filteredTasks, setFilteredTasks] = useState(tasks);

  function filterTasks(status) {
    setSelectedButton(status);
    if (status === "All Tasks") {
      getTasks();
      setFilteredTasks(tasks); // Show all tasks
    } else {
      setFilteredTasks(tasks.filter((task) => task.status === status));
    }
  }

  const verifyUser = async () => {
    try {
      await axios
        .post(`${BASE_URL}:8080/crm/verify`, {}, { withCredentials: true })
        .then((response) => {
          if (response.status == axios.HttpStatusCode.Ok) {
            getTasks();
          }
        });
    } catch (error) {
      console.error(error);
      window.location.href = "/login";
    }
  };

  useEffect(() => {
    verifyUser();
  }, []);

  async function setTaskStatus(id, status) {
    try {
      await axios
        .patch(`${BASE_URL}:8080/task/status`, {
          id: id,
          status: status,
        })
        .then((response) => {
          getTasks();
        })
        .catch((error) => {});
      if (response.status == axios.HttpStatusCode.Ok) {
        getTasks();
      }
    } catch (error) {
      console.error(error);
    }
  }

  function getTasks() {
    try {
      axios
        .get(`${BASE_URL}:8080/task/all`, {
          params: {
            username: sessionStorage.getItem("loginInfo"),
          },
        })
        .then((response) => {
          if (response.status == axios.HttpStatusCode.Ok) {
            setTasks(response.data);
            console.log(tasks);
          }
        });
    } catch (error) {
      console.error(error);
    }
  }

  const handleTaskCompletion = (id) => {
    setFilteredTasks(
      tasks.map((task) =>
        task.id === id ? { ...task, completed: !task.completed } : task
      )
    );
    setTaskStatus(id, "COMPLETED");
  };
  const handleTaskAdd = () => {
    setIsAddTaskFormVisible(true);
  };

  return (
    <div className="flex h-screen w-screen">
      <nav className="flex flex-col bg-gray-900 text-gray-400 dark:bg-gray-950 dark:text-gray-400">
        <div className="flex h-16 items-center justify-center border-b border-gray-800 dark:border-gray-800">
          <Link
            href="/"
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
                to="/tasks"
                className="flex items-center gap-3 rounded-lg px-4 py-2 bg-gray-800 text-gray-50 dark:bg-gray-800 dark:text-gray-50"
              >
                <SquareCheckIcon className="h-5 w-5" />
                <span>Tasks</span>
              </Link>
            </li>
            <li>
              <Link
                to="/products"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <ProductIcon className="h-5 w-5" />
                <span>Products</span>
              </Link>
            </li>
            <li>
              <Link
                to="/messanger"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <MessangerIcon className="h-5 w-5" />
                <span>Messanger</span>
              </Link>
            </li>
            <li>
              <Link
                to="/logout"
                className="flex items-center gap-3 rounded-lg px-4 py-2 hover:bg-gray-800 hover:text-gray-50 dark:hover:bg-gray-800 dark:hover:text-gray-50"
              >
                <LogoutIcon className="h-5 w-5" />
                <span>Logout</span>
              </Link>
            </li>
          </ul>
        </div>
      </nav>
      <main className="flex-1 bg-gray-100 dark:bg-gray-950">
        <header className="flex h-16 items-center justify-between border-b bg-white px-6 dark:border-gray-800 dark:bg-gray-950">
          <h1 className="text-lg font-medium">Tasks</h1>
          <div className="flex items-center gap-4">
            <form className="relative">
              <SearchIcon className="absolute left-2.5 top-2.5 h-4 w-4 text-gray-500 dark:text-gray-400" />
              <Input
                type="search"
                placeholder="Search deals..."
                className="pl-8 pr-4 h-9 rounded-md bg-gray-100 dark:bg-gray-800 dark:text-gray-50"
              />
            </form>
            <DropdownMenu
              trigger={
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
              }
            >
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
        <div>
          <div className="grid gap-4">
            <div className="flex items-center justify-start p-4">
              <div className="flex space-x-4 p-4">
                <Button
                  variant="outline"
                  OnClick={() => filterTasks("All Tasks")}
                  className={
                    selectedButton === "AllTasks"
                      ? "bg-blue-500 text-white"
                      : ""
                  }
                >
                  All Tasks
                </Button>
                <Button
                  variant="outline"
                  OnClick={() => filterTasks("TODO")}
                  className={
                    selectedButton === "TODO" ? "bg-blue-500 text-white" : ""
                  }
                >
                  TODO
                </Button>
                <Button
                  variant="outline"
                  OnClick={() => filterTasks("IN_PROGRESS")}
                  className={
                    selectedButton === "IN_PROGRESS"
                      ? "bg-blue-500 text-white"
                      : ""
                  }
                >
                  In Progress
                </Button>
                <Button
                  variant="outline"
                  OnClick={() => filterTasks("COMPLETED")}
                  className={
                    selectedButton === "COMPLETED"
                      ? "bg-blue-500 text-white"
                      : ""
                  }
                >
                  Completed
                </Button>
              </div>
              <Button className="rounded-full" OnClick={handleTaskAdd}>
                <PlusIcon className="h-4 w-4 mr-2" />
                Add Task
              </Button>
              {isAddTaskFormVisible && (
                <AddTaskForm onClose={() => setIsAddTaskFormVisible(false)} />
              )}
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-2">
              {filteredTasks.map((task) => (
                <Card
                  key={task.id}
                  className={`${
                    task.completed ? "opacity-50" : ""
                  } bg-slate-700 rounded-lg p-2`}
                >
                  <CardHeader>
                    <CardTitle className="uppercase font-bold text-lg">
                      {task.name}
                    </CardTitle>
                    <CardDescription>{task.description}</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <p>{task.description}</p>
                    <p>От: {new Date(...task.createdAt).toLocaleString()}</p>
                    <p>До: {new Date(...task.deadline).toLocaleString()}</p>
                    <p>
                      Неактуально после:{" "}
                      {new Date(...task.expiresAt).toLocaleString()}
                    </p>
                  </CardContent>
                  <CardFooter className="space-x-4">
                    {(task.status === "TODO" ||
                      task.status === "IN_PROGRESS") && (
                      <Button
                        variant={task.completed ? "secondary" : "primary"}
                        className="rounded-lg"
                        OnClick={() => handleTaskCompletion(task.id)}
                      >
                        Завершить
                      </Button>
                    )}
                    {(task.status === "TODO" ||
                      task.status === "COMPLETED") && (
                      <Button
                        variant={
                          task.status === "IN_PROGRESS"
                            ? "primary"
                            : "secondary"
                        }
                        className="rounded-lg"
                        OnClick={() => setTaskStatus(task.id, "IN_PROGRESS")}
                      >
                        Приступить
                      </Button>
                    )}
                    <p className="text-sm text-white">Status: {task.status}</p>
                  </CardFooter>
                </Card>
              ))}
            </div>
          </div>
        </div>
      </main>
    </div>
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

function FilePenIcon(props) {
  return (
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
        d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
      />
    </svg>
  );
}

function ProductIcon(props) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      viewBox="0 0 24 24"
      strokeWidth="1.5"
      stroke="currentColor"
      className="size-6"
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 0 0-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 0 0-16.536-1.84M7.5 14.25 5.106 5.272M6 20.25a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Zm12.75 0a.75.75 0 1 1-1.5 0 .75.75 0 0 1 1.5 0Z"
      />
    </svg>
  );
}

function PlusIcon(props) {
  return (
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
        d="M12 4.5v15m7.5-7.5h-15"
      />
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

function MessangerIcon(props) {
  return (
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
        d="M20.25 8.511c.884.284 1.5 1.128 1.5 2.097v4.286c0 1.136-.847 2.1-1.98 2.193-.34.027-.68.052-1.02.072v3.091l-3-3c-1.354 0-2.694-.055-4.02-.163a2.115 2.115 0 0 1-.825-.242m9.345-8.334a2.126 2.126 0 0 0-.476-.095 48.64 48.64 0 0 0-8.048 0c-1.131.094-1.976 1.057-1.976 2.192v4.286c0 .837.46 1.58 1.155 1.951m9.345-8.334V6.637c0-1.621-1.152-3.026-2.76-3.235A48.455 48.455 0 0 0 11.25 3c-2.115 0-4.198.137-6.24.402-1.608.209-2.76 1.614-2.76 3.235v6.226c0 1.621 1.152 3.026 2.76 3.235.577.075 1.157.14 1.74.194V21l4.155-4.155"
      />
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
