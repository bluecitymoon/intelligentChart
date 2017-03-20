(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFansHobbyDeleteController',PersonFansHobbyDeleteController);

    PersonFansHobbyDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonFansHobby'];

    function PersonFansHobbyDeleteController($uibModalInstance, entity, PersonFansHobby) {
        var vm = this;

        vm.personFansHobby = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonFansHobby.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
