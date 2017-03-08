(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonEndorsementDialogController', PersonEndorsementDialogController);

    PersonEndorsementDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonEndorsement', 'Person'];

    function PersonEndorsementDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonEndorsement, Person) {
        var vm = this;

        vm.personEndorsement = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personEndorsement.id !== null) {
                PersonEndorsement.update(vm.personEndorsement, onSaveSuccess, onSaveError);
            } else {
                PersonEndorsement.save(vm.personEndorsement, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personEndorsementUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
