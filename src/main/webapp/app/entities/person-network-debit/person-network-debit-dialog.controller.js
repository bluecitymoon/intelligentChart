(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonNetworkDebitDialogController', PersonNetworkDebitDialogController);

    PersonNetworkDebitDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonNetworkDebit', 'Person'];

    function PersonNetworkDebitDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonNetworkDebit, Person) {
        var vm = this;

        vm.personNetworkDebit = entity;
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
            if (vm.personNetworkDebit.id !== null) {
                PersonNetworkDebit.update(vm.personNetworkDebit, onSaveSuccess, onSaveError);
            } else {
                PersonNetworkDebit.save(vm.personNetworkDebit, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personNetworkDebitUpdate', result);
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
