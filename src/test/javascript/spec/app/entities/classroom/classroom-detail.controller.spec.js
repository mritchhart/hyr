'use strict';

describe('Controller Tests', function() {

    describe('Classroom Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockClassroom, MockTeacher, MockStudent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockClassroom = jasmine.createSpy('MockClassroom');
            MockTeacher = jasmine.createSpy('MockTeacher');
            MockStudent = jasmine.createSpy('MockStudent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Classroom': MockClassroom,
                'Teacher': MockTeacher,
                'Student': MockStudent
            };
            createController = function() {
                $injector.get('$controller')("ClassroomDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hopeRanchLearningAcademyApp:classroomUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
