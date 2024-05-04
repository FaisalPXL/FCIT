import os.path
import numpy as np
from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Dense, Flatten
from keras.preprocessing import image
from keras.preprocessing.image import ImageDataGenerator
from matplotlib import pyplot as plt

# 1- Loading data
data_dir = 'fruits/dataset/train'
test_dir = 'fruits/dataset/test'

# Create an ImageDataGenerator and rescale the images from 0 to 1 instead of 0 to 255
datagen = ImageDataGenerator(rescale=1./255)

# Use the generator for training data
train = datagen.flow_from_directory(data_dir, target_size=(100, 100), batch_size=32, class_mode='categorical')
# Use the generator for validation data
val = datagen.flow_from_directory(test_dir, target_size=(100, 100), batch_size=32, class_mode='categorical')

# 3- The Model

# Build the model
model = Sequential()
# Layer 1
# Adding convolution
# 16 is num of filters, size of filter 3x3 pixels, move 1 pixel each time
# 'relu' activation is converting any negative num to 0 to only preserve positive numbers
model.add(Conv2D(16, (3,3), 1, activation='relu', input_shape=(100, 100, 3)))
# Here we take maximum data after 'relu' activation, it takes 2x2 data and choose max value of them
# to reduce the num of data
model.add(MaxPooling2D())
# Layer 2
model.add(Conv2D(32, (3,3), 1, activation='relu'))
model.add(MaxPooling2D())
# Layer 3
model.add(Conv2D(16, (3,3), 1, activation='relu'))
model.add(MaxPooling2D())
# Condensing the data into single dimension
model.add(Flatten())

model.add(Dense(256, activation='relu'))
model.add(Dense(6, activation='softmax'))
# We used 'adam' optimizer, the loss we used is for multi classification
# The metric we want to track is accuracy (how well our model is classifying)
model.compile('adam', loss="categorical_crossentropy", metrics=['accuracy'])

model.summary()

# Training
history = model.fit(train, epochs=10, validation_data=val)
# visualize the model accuracy
fig = plt.Figure()
plt.plot(history.history['loss'], color='teal', label='loss')
plt.plot(history.history['val_loss'], color='orange', label='val_loss')
fig.suptitle('Loss', fontsize=20)
plt.legend(loc="upper left")
plt.show()


def test_image(image_path):
    test_image = image.load_img(image_path, target_size=(100, 100))
    test_image = image.img_to_array(test_image)
    test_image/=255
    test_image = np.expand_dims(test_image, axis=0)
    result = model.predict(test_image)
    predictedClassIndex = np.argmax(result)
    if predictedClassIndex == 0:
        return "Fresh Apple"
    elif predictedClassIndex == 1:
        return "Fresh Banana"
    elif predictedClassIndex == 2:
        return "Fresh Orange"
    elif predictedClassIndex == 3:
        return "Rotten Apple"
    elif predictedClassIndex == 4:
        return "Rotten Banana"
    elif predictedClassIndex == 5:
        return "Rotten Orange"
    else:
        return "Unknown"

test_image("Path of the testing image")

#To save the Model
model.save(os.path.join('models', 'fruitsRottenOrNot.h5'))