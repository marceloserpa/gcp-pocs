const sql = require('mssql')
const config = {
  server: "MY_IP",
  database: "marcelodb",
  user:"user",
  password: "password",
  port: 1433,
  options: {
    encrypt: false
  }
};

(async function () {
  console.log("starting")
    try {
        console.log("connect on SQL Server")
        let pool = await sql.connect(config)

        console.log("insert one book")
        const request = new sql.Request()
        request.query("insert into books (title, author) values ('Stephen King', 'The Shining')").then(result => {
            console.log(result.rowsAffected)
        })

        console.log("query all books")
        let result = await pool.request().query('select * from books')

        console.log(result.recordset)

    } catch (err) {
      console.log(err)
        // ... error checks
    }
    console.log("ending")
})()
