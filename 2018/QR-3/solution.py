input = open("./input/ethan_searches_for_a_string.txt", "r")
input_data = input.read().splitlines()
input.close()

output = open("./output/hack_ethan.txt",'w')

test_cases = int(input_data[0])
data_start = 1
for t in range(0, test_cases):
  A = input_data[data_start]
  # print("A:", A)

  # (1) To determine possibility,
  # Find repeated char(n) with different char(n+1)
  # Case #1: AB, AC = true
  # Case #2: OO, OK = false (And, point #2 violated)
  # Case #3: XY, XZ = true
  # Case #4: FB, FB = false
  prefix = A[0]
  # print("Prefix check:", prefix)
  repeated_index = A.find(prefix, 1)

  possibility = False
  repeated_length = -1
  while repeated_index != -1 and not possibility:
    # Record last search
    last_search = repeated_index
    # print("Checking index:", repeated_index)
    substring = A[repeated_index:]

    # Checking:
    length = 1
    while length <= len(substring) and not possibility:
      if A[0:length] != substring[0:length]:
        possibility = True
        repeated_length = repeated_index
        # print("Possible with:", A[0:length], substring[0:length])
      length += 1

    # Reset
    repeated_index = A.find(prefix, last_search + 1)

  # (2) Fool Ethan's program,
  # By repeating patterns
  # Case #1: ABABACUS
  answer = 'Impossible'
  if possibility:
    # print("This is possible, with repetition length:", repeated_length)
    repeat_pattern = A[0:repeated_length]
    answer = repeat_pattern + A

  # MY ANSWER:
  outline = "Case #" + str(t+1) + ": " + answer
  # print(outline)
  if len(outline) > 10000:
    print("##### WARNING: exceed 10k chars!")
  output.write(outline + "\n")

  data_start += 1

output.close()