input = open("./input/tourist.txt", "r")
input_data = input.read().splitlines()
input.close()
# print(len(input_data))

output = open("./output/my_tourist.txt",'w')

test_cases = int(input_data[0])
# print(test_cases)

# Test case loop
data_start = 1
for t in range(0, test_cases):
  arg = input_data[data_start].split(' ')
  n = int(arg[0])
  k = int(arg[1])
  v = int(arg[2])
  # print('n', n)
  # print('k', k)
  # print('v', v)
  attractions = input_data[data_start + 1 : data_start + n + 1]
  # print('attractions', attractions)

  # Find next visit
  total_visits = k * (v-1)
  next_visit_index = total_visits % n

  selections = []
  # Choose by index
  for select in range(k):
    selections.append(next_visit_index % len(attractions))
    next_visit_index += 1
  # print("Selections:", selections)

  # Choose attractions with order in popularity
  selections.sort()
  selected_attrs = []
  for select in range(k):
    selected_attrs.append(attractions[selections[select]])
  outline = "Case #" + str(t+1) + ": " + str.join(' ', selected_attrs)
  print(outline)
  # MY ANSWER:
  output.write(outline + "\n")

  # Go to next test case
  data_start += (n+1)

output.close()