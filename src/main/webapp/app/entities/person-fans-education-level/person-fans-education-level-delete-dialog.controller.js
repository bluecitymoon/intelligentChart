(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansEducationLevelDeleteController',PersonFansEducationLevelDeleteController);

    PersonFansEducationLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonFansEducationLevel'];

    function PersonFansEducationLevelDeleteController($uibModalInstance, entity, PersonFansEducationLevel) {
        var vm = this;

        vm.personFansEducationLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonFansEducationLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
