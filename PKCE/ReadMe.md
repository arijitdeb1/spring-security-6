🚨𝗢𝗔𝘂𝘁𝗵 𝟮.𝟭 𝗺𝗮𝗻𝗱𝗮𝘁𝗲𝘀 𝗣𝗞𝗖𝗘 — 𝘀𝗼 𝘄𝗵𝗮𝘁 𝗶𝘀 𝗣𝗞𝗖𝗘?🚨



⚠️ OAuth was originally designed assuming confidential clients (backend servers) that can safely store a 𝗰𝗹𝗶𝗲𝗻𝘁 𝘀𝗲𝗰𝗿𝗲𝘁.

However, modern apps like - 𝗠𝗼𝗯𝗶𝗹𝗲 𝗮𝗽𝗽𝘀, 𝗦𝗶𝗻𝗴𝗹𝗲 𝗣𝗮𝗴𝗲 𝗔𝗽𝗽𝗹𝗶𝗰𝗮𝘁𝗶𝗼𝗻𝘀 (𝗥𝗲𝗮𝗰𝘁, 𝗔𝗻𝗴𝘂𝗹𝗮𝗿, 𝗩𝘂𝗲), 𝗗𝗲𝘀𝗸𝘁𝗼𝗽 𝗮𝗽𝗽𝘀 are mostly public clients.



❌Public apps run on users’ devices, any long-term secret stored in them can be compromised.



💡𝗣𝗞𝗖𝗘 (𝗣𝗿𝗼𝗼𝗳 𝗞𝗲𝘆 𝗳𝗼𝗿 𝗖𝗼𝗱𝗲 𝗘𝘅𝗰𝗵𝗮𝗻𝗴𝗲) adds proof of possession to the flow. Instead of relying on a static client secret, PKCE uses:

 • a 𝗰𝗼𝗱𝗲_𝘃𝗲𝗿𝗶𝗳𝗶𝗲𝗿 (random secret generated at runtime)

 • a 𝗰𝗼𝗱𝗲_𝗰𝗵𝗮𝗹𝗹𝗲𝗻𝗴𝗲 (hashed version of the verifier)



🔎Here’s a step-by-step view of the flow, assuming basic familiarity with OAuth:



𝗦𝘁𝗲𝗽 𝟭: App generates random alphanumeric 𝗰𝗼𝗱𝗲_𝘃𝗲𝗿𝗶𝗳𝗶𝗲𝗿 and stores in app memory.

𝗦𝘁𝗲𝗽 𝟮: Generate 𝗰𝗼𝗱𝗲_𝗰𝗵𝗮𝗹𝗹𝗲𝗻𝗴𝗲 which is a one-way SHA-256 hash of the code_verifier (Base64-URL encoded), so the original verifier cannot be derived from it.

𝗦𝘁𝗲𝗽 𝟯: App sends user for Login e.g. Google Login with below details 

        • client_id

        • redirect_uri

        • 𝗰𝗼𝗱𝗲_𝗰𝗵𝗮𝗹𝗹𝗲𝗻𝗴𝗲

        • 𝗰𝗼𝗱𝗲_𝗰𝗵𝗮𝗹𝗹𝗲𝗻𝗴𝗲_𝗺𝗲𝘁𝗵𝗼𝗱=SHA-256

𝗦𝘁𝗲𝗽 𝟰: User logs in → Google returns Authorization Code as below - 

        𝗿𝗲𝗱𝗶𝗿𝗲𝗰𝘁?𝗰𝗼𝗱𝗲=𝟭𝟮𝟯𝟰-𝘅𝘆𝘇

𝗦𝘁𝗲𝗽 𝟱: Now, App sends 𝗧𝗼𝗸𝗲𝗻 𝗿𝗲𝗾𝘂𝗲𝘀𝘁 to Google along with below details

         • authorization_code

         • 𝗰𝗼𝗱𝗲_𝘃𝗲𝗿𝗶𝗳𝗶𝗲𝗿

         • client_id

         • redirect_uri

𝗦𝘁𝗲𝗽 𝟲: Google verifies that using the 𝗰𝗼𝗱𝗲_𝗰𝗵𝗮𝗹𝗹𝗲𝗻𝗴𝗲_𝗺𝗲𝘁𝗵𝗼𝗱 hashing algo received earlier and generates the Token and Refresh Token.

         𝗵𝗮𝘀𝗵(𝗰𝗼𝗱𝗲_𝘃𝗲𝗿𝗶𝗳𝗶𝗲𝗿) == 𝗰𝗼𝗱𝗲_𝗰𝗵𝗮𝗹𝗹𝗲𝗻𝗴𝗲



🚀Henceforth, the authorization code and 𝗰𝗼𝗱𝗲_𝘃𝗲𝗿𝗶𝗳𝗶𝗲𝗿 are invalidated, and all further communication uses the access token over HTTPS. 🙌

https://www.oauth.com/oauth2-servers/pkce/
